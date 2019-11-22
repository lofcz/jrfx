package com.company;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.List;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 *  Class RawConsoleInput - humbly borrowed from http://www.source-code.biz/snippets/java/RawConsoleInput/
 *  Used to get single character and nonblocking input
 *
 *  This class is a part of (Ad)venture project
 *
 *@author     Christian d'Heureuse
 *@version    1.0
 *@created    2015
 */
public class RawConsoleInput {

    private static final boolean           isWindows     = System.getProperty("os.name").startsWith("Windows");
    private static final int               invalidKey    = 0xFFFE;
    private static final String            invalidKeyStr = String.valueOf((char)invalidKey);

    private static boolean                 initDone;
    private static boolean                 stdinIsConsole;
    private static boolean                 consoleModeAltered;

    public static int read (boolean wait) throws IOException {
        if (isWindows) {
            return readWindows(wait); }
        else {
            return readUnix(wait); }}

    public static void resetConsoleMode() throws IOException {
        if (isWindows) {
            resetConsoleModeWindows(); }
        else {
            resetConsoleModeUnix(); }}

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook( new Thread() {
            public void run() {
                shutdownHook(); }}); }

    private static void shutdownHook() {
        try {
            resetConsoleMode(); }
        catch (Exception e) {}}

    private static Msvcrt        msvcrt;
    private static Kernel32      kernel32;
    private static Pointer       consoleHandle;
    private static int           originalConsoleMode;

    private static int readWindows (boolean wait) throws IOException {
        initWindows();
        if (!stdinIsConsole) {
            int c = msvcrt.getwchar();
            if (c == 0xFFFF) {
                c = -1; }
            return c; }
        consoleModeAltered = true;
        setConsoleMode(consoleHandle, Kernel32Defs.ENABLE_MOUSE_INPUT);
        if (!wait && msvcrt._kbhit() == 0) {
            return -2; }
        return getwch(); }

    private static int getwch() {
        int c = msvcrt._getwch();
        if (c == 0 || c == 0xE0) {
            c = msvcrt._getwch();
            if (c >= 0 && c <= 0x18FF) {
                return 0xE000 + c; }
            return invalidKey; }
        if (c < 0 || c > 0xFFFF) {
            return invalidKey; }
        return c; }

    private static synchronized void initWindows() throws IOException {
        if (initDone) {
            return; }
        msvcrt = (Msvcrt)Native.loadLibrary("msvcrt", Msvcrt.class);
        kernel32 = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);
        try {
            consoleHandle = getStdInputHandle();
            originalConsoleMode = getConsoleMode(consoleHandle);
            stdinIsConsole = true; }
        catch (IOException e) {
            stdinIsConsole = false; }
        if (stdinIsConsole) {
            registerShutdownHook(); }
        initDone = true; }

    private static Pointer getStdInputHandle() throws IOException {
        Pointer handle = kernel32.GetStdHandle(Kernel32Defs.STD_INPUT_HANDLE);
        if (Pointer.nativeValue(handle) == 0 || Pointer.nativeValue(handle) == Kernel32Defs.INVALID_HANDLE_VALUE) {
            throw new IOException("GetStdHandle(STD_INPUT_HANDLE) failed."); }
        return handle; }

    private static int getConsoleMode (Pointer handle) throws IOException {
        IntByReference mode = new IntByReference();
        int rc = kernel32.GetConsoleMode(handle, mode);
        if (rc == 0) {
            throw new IOException("GetConsoleMode() failed."); }
        return mode.getValue(); }

    private static void setConsoleMode (Pointer handle, int mode) throws IOException {
        int rc = kernel32.SetConsoleMode(handle, mode);
        if (rc == 0) {
            throw new IOException("SetConsoleMode() failed."); }}

    private static void resetConsoleModeWindows() throws IOException {
        if (!initDone || !stdinIsConsole || !consoleModeAltered) {
            return; }
        setConsoleMode(consoleHandle, originalConsoleMode);
        consoleModeAltered = false; }

    private static interface Msvcrt extends Library {
        int _kbhit();
        int _getwch();
        int getwchar(); }

    private static class Kernel32Defs {
        static final int  STD_INPUT_HANDLE       = -10;
        static final long INVALID_HANDLE_VALUE   = 0xFFFFFFFFL;
        static final int  ENABLE_PROCESSED_INPUT = 0x0001;
        static final int  ENABLE_LINE_INPUT      = 0x0002;
        static final int  ENABLE_ECHO_INPUT      = 0x0004;
        static final int  ENABLE_WINDOW_INPUT    = 0x0008;
        static final int  ENABLE_MOUSE_INPUT     = 0x0010;
    }

    private static interface Kernel32 extends Library {
        int GetConsoleMode (Pointer hConsoleHandle, IntByReference lpMode);
        int SetConsoleMode (Pointer hConsoleHandle, int dwMode);
        Pointer GetStdHandle (int nStdHandle); }

    private static final int               stdinFd = 0;
    private static Libc                    libc;
    private static CharsetDecoder          charsetDecoder;
    private static Termios                 originalTermios;
    private static Termios                 rawTermios;
    private static Termios                 intermediateTermios;

    private static int readUnix (boolean wait) throws IOException {
        initUnix();
        if (!stdinIsConsole) {
            return readSingleCharFromByteStream(System.in); }
        consoleModeAltered = true;
        setTerminalAttrs(stdinFd, rawTermios);
        try {
            if (!wait && System.in.available() == 0) {
                return -2; }
            return readSingleCharFromByteStream(System.in); }
        finally {
            setTerminalAttrs(stdinFd, intermediateTermios); }}

    private static Termios getTerminalAttrs (int fd) throws IOException {
        Termios termios = new Termios();
        try {
            int rc = libc.tcgetattr(fd, termios);
            if (rc != 0) {
                throw new RuntimeException("tcgetattr() failed."); }}
        catch (LastErrorException e) {
            throw new IOException("tcgetattr() failed.", e); }
        return termios; }

    private static void setTerminalAttrs (int fd, Termios termios) throws IOException {
        try {
            int rc = libc.tcsetattr(fd, LibcDefs.TCSANOW, termios);
            if (rc != 0) {
                throw new RuntimeException("tcsetattr() failed."); }}
        catch (LastErrorException e) {
            throw new IOException("tcsetattr() failed.", e); }}

    private static int readSingleCharFromByteStream (InputStream inputStream) throws IOException {
        byte[] inBuf = new byte[4];
        int    inLen = 0;
        while (true) {
            if (inLen >= inBuf.length) {
                return invalidKey; }
            int b = inputStream.read();
            if (b == -1) {
                return -1; }
            inBuf[inLen++] = (byte)b;
            int c = decodeCharFromBytes(inBuf, inLen);
            if (c != -1) {
                return c; }}}


    private static synchronized int decodeCharFromBytes (byte[] inBytes, int inLen) {
        charsetDecoder.reset();
        charsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
        charsetDecoder.replaceWith(invalidKeyStr);
        ByteBuffer in = ByteBuffer.wrap(inBytes, 0, inLen);
        CharBuffer out = CharBuffer.allocate(1);
        charsetDecoder.decode(in, out, false);
        if (out.position() == 0) {
            return -1; }
        return out.get(0); }

    private static synchronized void initUnix() throws IOException {
        if (initDone) {
            return; }
        libc = (Libc)Native.loadLibrary("c", Libc.class);
        stdinIsConsole = libc.isatty(stdinFd) == 1;
        charsetDecoder = Charset.defaultCharset().newDecoder();
        if (stdinIsConsole) {
            originalTermios = getTerminalAttrs(stdinFd);
            rawTermios = new Termios(originalTermios);
            rawTermios.c_lflag &= ~(LibcDefs.ICANON | LibcDefs.ECHO | LibcDefs.ECHONL | LibcDefs.ISIG);
            intermediateTermios = new Termios(rawTermios);
            intermediateTermios.c_lflag |= LibcDefs.ICANON;
            registerShutdownHook(); }
        initDone = true; }

    private static void resetConsoleModeUnix() throws IOException {
        if (!initDone || !stdinIsConsole || !consoleModeAltered) {
            return; }
        setTerminalAttrs(stdinFd, originalTermios);
        consoleModeAltered = false; }

    protected static class Termios extends Structure {
        public int      c_iflag;
        public int      c_oflag;
        public int      c_cflag;
        public int      c_lflag;
        public byte     c_line;
        public byte[]   filler = new byte[64];
        @Override protected List<String> getFieldOrder() {
            return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "filler"); }
        Termios() {}
        Termios (Termios t) {
            c_iflag = t.c_iflag;
            c_oflag = t.c_oflag;
            c_cflag = t.c_cflag;
            c_lflag = t.c_lflag;
            c_line  = t.c_line;
            filler  = t.filler.clone(); }}

    private static class LibcDefs {
        static final int ISIG    = 0000001;
        static final int ICANON  = 0000002;
        static final int ECHO    = 0000010;
        static final int ECHONL  = 0000100;
        static final int TCSANOW = 0; }

    private static interface Libc extends Library {
        int tcgetattr (int fd, Termios termios) throws LastErrorException;
        int tcsetattr (int fd, int opt, Termios termios) throws LastErrorException;
        int isatty (int fd); }

}
