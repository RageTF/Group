package algorithms;

/**
 * Created by Rage on 18.05.2017.
 */
public class AlgorithmResult {

    private long mTime=-1;
    private int[] mPath;
    private int mLength=-1;
    private int mSize=-1;

    private String mName;

    public AlgorithmResult(String name, int size) {
        mName = name;
        mSize=size;
    }

    public int getSize() {
        return mSize;
    }

    public String getName() {
        return mName;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int[] getPath() {
        return mPath;
    }

    public void setPath(int[] path) {
        mPath = path;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }
}
