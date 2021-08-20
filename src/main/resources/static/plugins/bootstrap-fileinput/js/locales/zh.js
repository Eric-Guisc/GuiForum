/*!
 * FileInput Chinese Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 * @author kangqf <kangqingfei@gmail.com>
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 */
(function ($) {
    "use strict";

    $.fn.fileinputLocales['zh'] = {
        fileSingle: '鏂囦欢',
        filePlural: '涓枃浠�',
        browseLabel: '閫夋嫨 &hellip;',
        removeLabel: '绉婚櫎',
        removeTitle: '娓呴櫎閫変腑鏂囦欢',
        cancelLabel: '鍙栨秷',
        cancelTitle: '鍙栨秷杩涜涓殑涓婁紶',
        pauseLabel: 'Pause',
        pauseTitle: 'Pause ongoing upload',
        uploadLabel: '涓婁紶',
        uploadTitle: '涓婁紶閫変腑鏂囦欢',
        msgNo: '娌℃湁',
        msgNoFilesSelected: '鏈€夋嫨鏂囦欢',
        msgPaused: 'Paused',
        msgCancelled: '鍙栨秷',
        msgPlaceholder: '閫夋嫨 {files} ...',
        msgZoomModalHeading: '璇︾粏棰勮',
        msgFileRequired: '蹇呴』閫夋嫨涓€涓枃浠朵笂浼�.',
        msgSizeTooSmall: '鏂囦欢 "{name}" (<b>{size} KB</b>) 蹇呴』澶т簬闄愬畾澶у皬 <b>{minSize} KB</b>.',
        msgSizeTooLarge: '鏂囦欢 "{name}" (<b>{size} KB</b>) 瓒呰繃浜嗗厑璁稿ぇ灏� <b>{maxSize} KB</b>.',
        msgFilesTooLess: '浣犲繀椤婚€夋嫨鏈€灏� <b>{n}</b> {files} 鏉ヤ笂浼�. ',
        msgFilesTooMany: '閫夋嫨鐨勪笂浼犳枃浠朵釜鏁� <b>({n})</b> 瓒呭嚭鏈€澶ф枃浠剁殑闄愬埗涓暟 <b>{m}</b>.',
        msgTotalFilesTooMany: 'You can upload a maximum of <b>{m}</b> files (<b>{n}</b> files detected).',
        msgFileNotFound: '鏂囦欢 "{name}" 鏈壘鍒�!',
        msgFileSecured: '瀹夊叏闄愬埗锛屼负浜嗛槻姝㈣鍙栨枃浠� "{name}".',
        msgFileNotReadable: '鏂囦欢 "{name}" 涓嶅彲璇�.',
        msgFilePreviewAborted: '鍙栨秷 "{name}" 鐨勯瑙�.',
        msgFilePreviewError: '璇诲彇 "{name}" 鏃跺嚭鐜颁簡涓€涓敊璇�.',
        msgInvalidFileName: '鏂囦欢鍚� "{name}" 鍖呭惈闈炴硶瀛楃.',
        msgInvalidFileType: '涓嶆纭殑绫诲瀷 "{name}". 鍙敮鎸� "{types}" 绫诲瀷鐨勬枃浠�.',
        msgInvalidFileExtension: '涓嶆纭殑鏂囦欢鎵╁睍鍚� "{name}". 鍙敮鎸� "{extensions}" 鐨勬枃浠舵墿灞曞悕.',
        msgFileTypes: {
            'image': 'image',
            'html': 'HTML',
            'text': 'text',
            'video': 'video',
            'audio': 'audio',
            'flash': 'flash',
            'pdf': 'PDF',
            'object': 'object'
        },
        msgUploadAborted: '璇ユ枃浠朵笂浼犺涓',
        msgUploadThreshold: '澶勭悊涓� &hellip;',
        msgUploadBegin: '姝ｅ湪鍒濆鍖� &hellip;',
        msgUploadEnd: '瀹屾垚',
        msgUploadResume: 'Resuming upload &hellip;',
        msgUploadEmpty: '鏃犳晥鐨勬枃浠朵笂浼�.',
        msgUploadError: 'Upload Error',
        msgDeleteError: 'Delete Error',
        msgProgressError: '涓婁紶鍑洪敊',
        msgValidationError: '楠岃瘉閿欒',
        msgLoading: '鍔犺浇绗� {index} 鏂囦欢 鍏� {files} &hellip;',
        msgProgress: '鍔犺浇绗� {index} 鏂囦欢 鍏� {files} - {name} - {percent}% 瀹屾垚.',
        msgSelected: '{n} {files} 閫変腑',
        msgFoldersNotAllowed: '鍙敮鎸佹嫋鎷芥枃浠�! 璺宠繃 {n} 鎷栨嫿鐨勬枃浠跺す.',
        msgImageWidthSmall: '鍥惧儚鏂囦欢鐨�"{name}"鐨勫搴﹀繀椤绘槸鑷冲皯{size}鍍忕礌.',
        msgImageHeightSmall: '鍥惧儚鏂囦欢鐨�"{name}"鐨勯珮搴﹀繀椤昏嚦灏戜负{size}鍍忕礌.',
        msgImageWidthLarge: '鍥惧儚鏂囦欢"{name}"鐨勫搴︿笉鑳借秴杩噞size}鍍忕礌.',
        msgImageHeightLarge: '鍥惧儚鏂囦欢"{name}"鐨勯珮搴︿笉鑳借秴杩噞size}鍍忕礌.',
        msgImageResizeError: '鏃犳硶鑾峰彇鐨勫浘鍍忓昂瀵歌皟鏁淬€�',
        msgImageResizeException: '璋冩暣鍥惧儚澶у皬鏃跺彂鐢熼敊璇€�<pre>{errors}</pre>',
        msgAjaxError: '{operation} 鍙戠敓閿欒. 璇烽噸璇�!',
        msgAjaxProgressError: '{operation} 澶辫触',
        msgDuplicateFile: 'File "{name}" of same size "{size} KB" has already been selected earlier. Skipping duplicate selection.',
        msgResumableUploadRetriesExceeded:  'Upload aborted beyond <b>{max}</b> retries for file <b>{file}</b>! Error Details: <pre>{error}</pre>',
        msgPendingTime: '{time} remaining',
        msgCalculatingTime: 'calculating time remaining',
        ajaxOperations: {
            deleteThumb: '鍒犻櫎鏂囦欢',
            uploadThumb: '涓婁紶鏂囦欢',
            uploadBatch: '鎵归噺涓婁紶',
            uploadExtra: '琛ㄥ崟鏁版嵁涓婁紶'
        },
        dropZoneTitle: '鎷栨嫿鏂囦欢鍒拌繖閲� &hellip;<br>鏀寔澶氭枃浠跺悓鏃朵笂浼�',
        dropZoneClickTitle: '<br>(鎴栫偣鍑粄files}鎸夐挳閫夋嫨鏂囦欢)',
        fileActionSettings: {
            removeTitle: '鍒犻櫎鏂囦欢',
            uploadTitle: '涓婁紶鏂囦欢',
            downloadTitle: '涓嬭浇鏂囦欢',
            uploadRetryTitle: '閲嶈瘯',
            zoomTitle: '鏌ョ湅璇︽儏',
            dragTitle: '绉诲姩 / 閲嶇疆',
            indicatorNewTitle: '娌℃湁涓婁紶',
            indicatorSuccessTitle: '涓婁紶',
            indicatorErrorTitle: '涓婁紶閿欒',
            indicatorPausedTitle: 'Upload Paused',
            indicatorLoadingTitle:  '涓婁紶 &hellip;'
        },
        previewZoomButtonTitles: {
            prev: '棰勮涓婁竴涓枃浠�',
            next: '棰勮涓嬩竴涓枃浠�',
            toggleheader: '缂╂斁',
            fullscreen: '鍏ㄥ睆',
            borderless: '鏃犺竟鐣屾ā寮�',
            close: '鍏抽棴褰撳墠棰勮'
        }
    };
})(window.jQuery);