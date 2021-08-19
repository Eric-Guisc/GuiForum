package priv.gsc.guiforum.util;

public class GuiForumEnum {

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



}
