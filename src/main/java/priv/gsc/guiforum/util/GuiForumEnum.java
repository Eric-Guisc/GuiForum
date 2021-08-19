package priv.gsc.guiforum.util;

public class GuiForumEnum {

    // 邮箱激活状态
    public enum ACTIVATION {
        ACTIVATION_SUCCESS(0, "激活成功"),
        ACTIVATION_REPEAT(1, "重复激活"),
        ACTIVATION_FAILURE(2, "激活失败");

        private int code;
        private String remark;

        private ACTIVATION(int code, String remark) {
            this.code = code;
            this.remark = remark;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    // 记住我状态
    public enum REMEMBERME {
        DEFAULT_EXPIRED_SECONDS(3600 * 12, "半天有效期"),
        REMEMBER_EXPIRED_SECONDS(3600 * 24 * 10, "10天有效期");

        private int expiredSeconds;
        private String remark;

        private REMEMBERME(int expiredSeconds, String remark) {
            this.expiredSeconds = expiredSeconds;
            this.remark = remark;
        }

        public int getExpiredSeconds() {
            return expiredSeconds;
        }

        public void setExpiredSeconds(int expiredSeconds) {
            this.expiredSeconds = expiredSeconds;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }



}
