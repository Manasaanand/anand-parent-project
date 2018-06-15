package net.orfdev.integration.test.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.DeferredFileOutputStream;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import net.orfdev.integration.test.exception.CoverException;


public class IOUtil {

    private static final int DEFAULT_MEMORY_THRESHOLD = 25 * 1024 * 1024; // 25MB

    /**
     * Factory method for {@link SizeAwareBufferedInputStream}
     *
     * @param is
     * @return
     * @see SizeAwareBufferedInputStream
     */
    public static SizeAwareBufferedInputStream bufferAndCalculateSize(InputStream is) {
        return new SizeAwareBufferedInputStream(is);
    }

    /**
     * Factory method for {@link DeferredFileOutputStream}
     *
     * @param thresholdBytes
     * @return
     * @see IOUtil#toInputStream(DeferredFileOutputStream)
     */
    public static DeferredFileOutputStream deferredFileOutputStream(int thresholdBytes) {
        return new DeferredFileOutputStream(thresholdBytes, "deferred_os", ".dat", FileUtils.getTempDirectory());
    }

    /**
     * Factory method for {@link DeferredFileOutputStream}. Default memory threshold - 25 MB;
     *
     * @return
     * @see IOUtil#toInputStream(DeferredFileOutputStream)
     */
    public static DeferredFileOutputStream deferredFileOutputStream() {
        return deferredFileOutputStream(DEFAULT_MEMORY_THRESHOLD);
    }

    /**
     * Returns either ByteArrayInputStream if all the DeferredFileOutputStream data is in memory or file backed InputStream if the data was
     * saved to file system.
     *
     * @param os
     * @return
     * @throws IOException
     */
    public static SizeAwareInputStream toInputStream(DeferredFileOutputStream os) {
        try {
            if (os.isInMemory()) {
                return new SizeAwareInputStream(new ByteArrayInputStream(os.getData()), os.getByteCount());
            } else {
                Path file = os.getFile().toPath();
                return new SizeAwareInputStream(new BufferedInputStream(Files.newInputStream(file)), os.getByteCount());
            }
        } catch (IOException ex) {
            throw new CoverException("Failed to get InputStream from DeferredFileOutputStream (that's really strange and was not expected to happen at all)", ex);
        }
    }

    /**
     * Factory method for {@link LoopingByteInputStream}.
     *
     * @param length number of bytes that will be return by the resulting input stream before returning -1.
     * @param values
     * @return
     */
    public static LoopingByteInputStream loopingBytesInputStream(long length, byte... values) {
        return new LoopingByteInputStream(length, values);
    }


    /**
     * Simply gets a resource and reads it as a String.
     *
     * @param resourceName
     * @return
     */
    public static String resourceAsString(String resourceName) {
        try (InputStream is = IOUtil.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new CoverException("Resource '" + resourceName + "' does not exist");
            }
            return IOUtils.toString(is, Charset.forName("UTF-8"));

        } catch (IOException ex) {
            throw new CoverException("Failed to read the resource '" + resourceName + "'", ex);
        }
    }


    //---------------------- IOUtil helper classes --------------------------//


    public static class SizeAwareInputStream extends InputStream {

        protected Long size;
        protected InputStream is;
        protected long bytesRead = 0;

        public SizeAwareInputStream(InputStream is, Long size) {
            this.size = size;
            this.is = is;
        }

        protected SizeAwareInputStream() {

        }

        /**
         * Total size of the data that can be returned by this input stream (starting from its initialization). Note that the value of the
         * getSize() is set upon creation of the stream and is not changed when read() is called (i.e. it is the total size of the data, not
         * the remaining one). Use available() to get the number of bytes that could be read from the stream at the moment.
         *
         * @return
         */
        public long getSize() {
            return size;
        }


        /**
         * Number of bytes available to read from the stream.
         *
         * @return
         * @throws IOException
         */
        @Override
        public int available() throws IOException {
            return (int) (this.getSize() - bytesRead);
        }

        @Override
        public int read() throws IOException {
            bytesRead++;
            return is.read();
        }

        @Override
        public synchronized void mark(int readlimit) {
            is.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            is.reset();
        }

        @Override
        public boolean markSupported() {
            return is.markSupported();
        }

        protected void setSize(Long size) {
            this.size = size;
        }

        protected void setIs(InputStream is) {
            this.is = is;
        }

    }

    /**
     * Buffers the whole input stream and calculates its size, then creates a new InputStream backed by the created buffer. Buffer is
     * created either in memory or using temp file. File is used when number of bytes read reaches a threshold. Default threshold is 25MB.
     * mark/reset methods are supported
     *
     * @author konstantinkastanov
     */
    public static class SizeAwareBufferedInputStream extends SizeAwareInputStream {

        private static final Logger log = LogManager.getLogger(null, null);

        private byte[] buff = null;

        public SizeAwareBufferedInputStream(InputStream is) {
            this(is, DEFAULT_MEMORY_THRESHOLD);
        }

        public SizeAwareBufferedInputStream(byte[] buff) {
            this.buff = buff;
            this.size = (long) buff.length;
            this.is = new ByteArrayInputStream(buff);
        }

        /**
         * @param is
         * @param inMemoryThreshold number of bytes allowed to be buffered in memory.
         */
        public SizeAwareBufferedInputStream(InputStream is, int inMemoryThreshold) {
            Validate.notNull(is, "Input stream should not be null");
            bufferAndCalculateSize(is, inMemoryThreshold);
        }

        public long getSize() {

            return size;
        }

        public long getBytesRead() {
            return bytesRead;
        }

        /**
         * Returns parent input stream content. This method returns null if InputStream is larger than a threshold and is buffered in a temp
         * file.
         *
         * @return
         */
        public byte[] getBytes() {
            return buff;
        }

        private void bufferAndCalculateSize(InputStream is, int inMemoryThreshold) {
            try {
                DeferredFileOutputStream deferredOs = null;

                try (DeferredFileOutputStream os = deferredFileOutputStream(inMemoryThreshold)) {
                    deferredOs = os;
                    IOUtils.copy(is, os);
                }

                this.setSize(deferredOs.getByteCount());
                this.setIs(toInputStream(deferredOs));
            } catch (IOException ex) {
                throw new CoverException("Failed to calculated input stream size", ex);
            }
        }

        private InputStream toInputStream(DeferredFileOutputStream os) throws IOException {
            if (os.isInMemory()) {
                log.debug("Reading input stream content from in memory byte array.");
                buff = os.getData();
                return new ByteArrayInputStream(os.getData());
            } else {
                Path file = os.getFile().toPath();
//                log.debug("Reading input stream content from temp file: {}", file);
                return new BufferedInputStream(Files.newInputStream(file));
            }
        }

    }

    /**
     * Fixed length fixed values input stream. Returns a specified number of bytes
     * by iterating over the provided bytes array (multiple times if required).
     * mark/reset operations are supported.
     *
     * @author konstantinkastanov
     */
    public static class LoopingByteInputStream extends InputStream {

        private final long length;
        private final byte[] values;
        private int idx = 0;
        private long bytesRead = 0;
        private int markedIdx = -1;
        private long markedBytesRead = -1;

        public LoopingByteInputStream(long length, byte... values) {
            Validate.isTrue(values.length > 0, "At least one value should be provided");
            this.length = length;
            this.values = values;
        }

        @Override
        public int read() {
            if (bytesRead >= length) {
                return -1;
            }

            byte value = values[idx];
            idx = (idx + 1) % values.length;
            bytesRead++;
            return value;
        }

        @Override
        public int available() throws IOException {
            return (int) (length - bytesRead);
        }

        @Override
        public synchronized void mark(int readlimit) {
            markedIdx = idx; //Oh damn I am lazy - this value can EASILY be derived from markedBytesRead.
            markedBytesRead = bytesRead;
        }

        @Override
        public synchronized void reset() throws IOException {
            if (markedIdx == -1 || markedBytesRead == -1) {
                throw new IOException("Failed to reset. mark() was not called before");
            }
            idx = markedIdx;
            bytesRead = markedBytesRead;
        }

        @Override
        public boolean markSupported() {
            return true;
        }


    }
}
