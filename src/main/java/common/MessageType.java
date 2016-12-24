package common;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public enum MessageType {
    HISTORY_UPDATE, // 用于从文件读取历史笔迹时的信息，附带的内容为ArrayList<Page>
    PAGE_REPLACE,    // 用于替换DrawingPanel的Page时的信息，仅由DrawingPanel处理，附带的内容为Page
    PAGE_ALTERED,   // 用于提示某一页内容发生了较大变动，由DrawingPanel发出，给PreviewPanel，附带内容为Page
    FEEDBACK,       // 用于Model在操作成功/失败时进行反馈，附带的内容为String


}
