package mobi.medbook.android.ui.custom_views.graphic_key;

import android.util.Log;

import java.util.Locale;

public class VisitCodeGenerator {
    private int id;
    private long seed;
    private int[][] code;
    private Settings settings;
    private boolean useDebugSeed;
    //private SyncTimeManager syncTimeManager;

//    public VisitCodeGenerator() {
//        syncTimeManager = SyncTimeManager.getInstance();
//    }

    public VisitCodeGenerator(int id) {
        //this();
        this.id = id;
        settings = new Settings();
    }

    public VisitCodeGenerator(int id, Settings settings) {
        //this();
        this.id = id;
        this.settings = settings;
    }

    public long getCurrentSeed() {
        return seed;
    }

    public long getSupposedSeed() {
        return getCurrentValidTimeInterval() - id;
    }

    public int[][] generateNewCode() {
        generateSeed();

        generateCodeForCurrentSeed();

        //log(code);
        return code;
    }

    private void log(int[][] code) {
        StringBuilder stringBuilder = new StringBuilder(String.format("%s \n", seed));

        for (int i = 0; i < code.length; i++) {
            stringBuilder.append(String.format(Locale.ENGLISH, "%s : %s \n", code[i][0], code[i][1]));
        }
        Log.d(VisitCodeGenerator.class.getSimpleName(), "validTimeInterval: " + getCurrentValidTimeInterval());
        Log.d(VisitCodeGenerator.class.getSimpleName(), stringBuilder.toString());

    }

    private void generateCodeForCurrentSeed() {
        MRandom random = new MRandom(seed);

        code = new int[settings.codeLength][2];

        int codeLength = 0;

        int x;
        int y;
        int[] nextValue;

        while (codeLength < settings.codeLength) {

            if (codeLength == 0) {
                code[0][0] = random.nextInt(settings.columnCount);
                code[0][1] = random.nextInt(settings.rowCount);

                codeLength++;
                continue;
            }

            x = random.nextInt(3) - 1;
            y = random.nextInt(3) - 1;

            nextValue = new int[]{code[codeLength - 1][0] + x, code[codeLength - 1][1] + y};

            if (checkNextValue(nextValue, code, codeLength)) {
                code[codeLength] = nextValue;
                codeLength++;
            }
        }
    }

    private boolean checkNextValue(int[] xy, int[][] code, int currentCodeLength) {
        if (xy[0] < 0 || xy[1] < 0 || xy[0] >= settings.getColumnCount() || xy[1] >= settings.getRowCount()) {
            return false;
        }

        for (int i = 0; i < currentCodeLength; i++) {
            if (xy[0] == code[i][0] && xy[1] == code[i][1]) {
                return false;
            }
        }

        return true;
    }

    public void useDebugSeed(long seed) {
        useDebugSeed = true;
        this.seed = seed;
    }

    public void useDinamicSeed() {
        useDebugSeed = false;
    }

    private void generateSeed() {
        if (useDebugSeed) {
            return;
        }

        seed = getCurrentValidTimeInterval() - id;
    }


    public long getCurrentValidTimeInterval() {
        long time = System.currentTimeMillis() + 9000;//syncTimeManager.getSynchronizedTime();
        return countIntervalTime(time / 1000);
    }

    public long getNextValidTimeInterval() {
        return getCurrentValidTimeInterval() + settings.getValidIntervalSec();
    }

    public int[][] getLastCode() {
        return code;
    }

    private long countIntervalTime(long time) {
        long t = time % settings.validIntervalSec;
        return time - t;
    }


    public Settings getSettings() {
        return settings;
    }

    public static class Settings {
        private final static int DEFAULT_COLUMN_COUNT = 3;
        private final static int DEFAULT_ROW_COUNT = 3;
        private final static int DEFAULT_CODE_LENGTH = 3;
        private final static int MIN_ROW_COUNT = 2;
        private final static int MIN_COLUMN_COUNT = 2;
        private final static int DEFAULT_CODE_VALID_INTERVAL_SEC = 20; //sec

        private int columnCount;
        private int rowCount;
        private int codeLength;
        private long validIntervalSec;

        public void setValidIntervalSec(long validIntervalSec) {
            this.validIntervalSec = validIntervalSec;
        }

        public int getColumnCount() {
            return columnCount;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getCodeLength() {
            return codeLength;
        }

        public long getValidIntervalSec() {
            return validIntervalSec;
        }

        public Settings() {
            columnCount = DEFAULT_COLUMN_COUNT;
            rowCount = DEFAULT_ROW_COUNT;
            codeLength = DEFAULT_CODE_LENGTH;
            validIntervalSec = DEFAULT_CODE_VALID_INTERVAL_SEC;
        }

        public Settings(int columnCount, int rowCount) {
            this();
            if (rowCount < MIN_ROW_COUNT || columnCount < MIN_COLUMN_COUNT) {
                throw new RuntimeException("rowCount and columnCount can't be less then 2");
            }

            this.columnCount = columnCount;
            this.rowCount = rowCount;
        }

        /**
         * Current VisitCodeGenerator can rich dead end if codeLength is bigger then 4. Algorithm should be improved
         */


        public Settings(int columnCount, int rowCount, int codeLength, long validIntervalSec) {
            this(columnCount, rowCount);
            if (codeLength > columnCount * rowCount) {
                throw new RuntimeException("codeLength can't be bigger then number of all cells (columnCount*rowCount)");
            }
            //code length can't be bigger then 4 now to prevent dead end.
            this.codeLength = Math.min(4, codeLength);
            if (validIntervalSec <= 0) {
                throw new RuntimeException("validIntervalSec should be bigger then 0");
            }
            this.validIntervalSec = validIntervalSec;
        }
    }

    private class MRandom {
        private long seed;
        private static final long MULTIPLIER = 0x5DEECE66DL;
        private static final long INCREMENT = 0xBL;
        private static final long MASK = ((1L << 48) - 1);

        public MRandom(long seed) {
            this.seed = seed;
        }

        public int nextInt(int bound) {
            if (bound <= 0)
                throw new IllegalArgumentException("bound must be positive");

            if ((bound & -bound) == bound)  // i.e., bound is a power of 2
                return (int) ((bound * (long) next(31)) >> 31);

            int bits, val;
            do {
                bits = next(31);
                val = bits % bound;
            } while (bits - val + (bound - 1) < 0);
            return val;
        }

        public int nextInt() {
            return next(32);
        }

        protected int next(int bits) {
            seed = (seed * MULTIPLIER + INCREMENT) & MASK;

            return (int) (seed >>> (48 - bits));
        }
    }
}
