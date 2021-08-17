package priv.gsc.guiforum.entity;

public class PostTag {
    private int postId;
    private int tagId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "PostTag{" +
                "postId=" + postId +
                ", tagId=" + tagId +
                '}';
    }
}
