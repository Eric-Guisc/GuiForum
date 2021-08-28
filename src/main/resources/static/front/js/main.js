/**
 * 提示框
 * @param text
 * @param icon
 * @param hideAfter
 */
function showMsg(text, icon, hideAfter) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff'
    });
}

function showMsgAndReload(text, icon, hideAfter) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.reload();
        }
    });
}

function showMsgAndRedirect(text, icon, hideAfter, redirectUrl) {
    if (heading == undefined) {
        var heading = "提示";
    }
    $.toast({
        text: text,
        heading: heading,
        icon: icon,
        showHideTransition: 'fade',
        allowToastClose: true,
        hideAfter: hideAfter,
        stack: 1,
        position: 'top-center',
        textAlign: 'left',
        loader: true,
        loaderBg: '#ffffff',
        afterHidden: function () {
            window.location.href = redirectUrl;
        }
    });
}

/**
 * TAB点击
 */
$('.nav-link').click(function () {
    const a = $(this);
    const type = a.attr('data-type');
    const firstPageNo = 1;
    $('#tt-pageContent').attr('data-page', firstPageNo);
    $('#tt-pageContent').attr('data-type', type)

    $('#load-more-div').show();
    $('#no-data').hide();

    loadPostList(firstPageNo, type, false);
});

/**
 * 分页加载
 */
$('#load-more-btn').click(function () {
    const page = parseInt($('#tt-pageContent').attr('data-page')) + 1;
    const type = $('#tt-pageContent').attr('data-type');
    $('#tt-pageContent').attr('data-page', page);
    loadPostList(page, type, true);
});

/**
 * 加载数据
 */
function loadPostList(page, type, isAppend) {
    const id = $('#tt-pageContent').attr('data-id');
    $.ajax({
        type: 'GET',
        url: '/post/list',
        async: false,
        data: {
            'keywords': $('#keywords').val(),
            'page': page,
            'type': type,
            'id': id
        },
        success: function (data) {
            console.log(data);
            if (data.code == 0) {
                $('#no-data').show();
                $('#load-more-div').remove();
                if (data.result == "notLogin") {
                    window.location.href = "/login";
                    return;
                }
            } else {
                if (data.result.current == data.result.pages) {
                    $('#load-more-div').hide();
                    $('#no-data').show();
                }
                let html = '';
                $.each(data.result.records, function (i, item) {
                    html += '<div class="tt-item">' +
                        '                        <div class="tt-col-avatar">' +
                        '                            <img src="' + item.user.userAvatar + '" class="avatar" alt="">' +
                        '                        </div>' +
                        '                        <div class="tt-col-description">' +
                        '                            <h6 class="tt-title"><a href="/post/' + item.id + '">' +
                        '                                ' + item.postTitle + '' +
                        '                            </a></h6>' +
                        '                            <div class="row align-items-center no-gutters">' +
                        '                                <div class="col-11">' +
                        '                                    <ul class="tt-list-badge">';
                    $.each(item.tagList, function (j, tag) {
                        html += '<li><a href="/tag/' + tag.id + '"><span class="tt-badge">' + tag.tagName + '</span></a></li>';
                    })

                    html += '                                    </ul>' +
                        '                                </div>' +
                        '                            </div>' +
                        '                        </div>' +
                        '                        <div class="tt-col-category"><a href="/category/' + item.category.id + '"><span class="tt-color0' + (parseInt(item.category.cateSort % 9) + 1) + ' tt-badge">' + item.category.cateName + '</span></a></div>' +
                        '                        <div class="tt-col-value  hide-mobile">' + item.postLikes + '</div>' +
                        '                        <div class="tt-col-value tt-color-select  hide-mobile">' + item.commentSize + '</div>' +
                        '                        <div class="tt-col-value  hide-mobile">' + item.postViews + '</div>' +
                        '                        <div class="tt-col-value hide-mobile">' + item.createTimeStr + '</div>' +
                        '                    </div>';
                });

                if (isAppend) {
                    $('#tt-item-wrapper .tt-item:last').after(html);
                    // var h = $(document).height()-$(window).height();
                    // $(document).scrollTop(h);;
                } else {
                    $('#tt-item-wrapper').html(html);
                }


            }

        }
    });
};

/**
 * 回帖
 */
const entityId_post = $('#entityId').val();
const targetUserId_post = $('#targetUserId').val();
$('#comment-btn').click(function () {
    const entityType = $('#entityType').val();
    const entityId = $('#entityId').val();
    const targetUserId = $('#targetUserId').val();
    const content = $('#content').val();
    if (content.length < 2) {
        showMsg('多写一点吧', "error", 1000);
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/forum/comment/add',
        async: true,
        data: {
            'entityType': entityType,
            'entityId': entityId,
            'targetUserId': targetUserId,
            'content': content
        },
        success: function (data) {
            if (data.code == 1) {
                showMsgAndReload(data.msg, "success", 1000);
                setTimeout(function(){
                    window.location.reload();
                }, 1500);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});


/**
 * 回帖回复
 */
//$('#comment-btn').click(function () {
//    const entityType = $('#entityType').val();
//    const entityId = $('#entityId').val();
//    const targetUserId = $('#targetUserId').val();
//    const content = $('#content').val();
//    if (content.length < 2) {
//        showMsg(data.msg, "error", '多写一点吧');
//        return;
//    }
//    $.ajax({
//        type: 'POST',
//        url: '/forum/comment/add',
//        async: true,
//        data: {
//            'entityType': entityType,
//            'entityId': entityId,
//            'targetUserId': targetUserId,
//            'content': content
//        },
//        success: function (data) {
//            if (data.code == 1) {
//                showMsgAndReload(data.msg, "success", 1000);
//                setTimeout(function(){
//                    window.location.reload();
//                }, 1500);
//            } else {
//                showMsg(data.msg, "error", 1000);
//            }
//        }
//    });
//});

/**
 * 触发按钮
 */
$('.reply-btn').click(function () {
    const entityId = $(this).attr('data-id-1');
    const targetUserId = $(this).attr('data-id-2');
//    const userDisplayName = $('#comment-' + entityId + '-user').text();
    $('#entityType').val(2);
    $('#entityId').val(entityId);
    $('#targetUserId').val(targetUserId);
//    $('#content').attr('placeholder', '@' + userDisplayName + ': ');
    $('#comment-cancel-btn').show();
});

/**
 * 取消回复
 */
$('#comment-cancel-btn').click(function () {
    $('#entityType').val(1);
    $('#entityId').val(entityId_post);
    $('#targetUserId').val(targetUserId_post);
//    $('#commentContent').val('');
    $('#comment-cancel-btn').hide();
});

/**
 * 评论点赞
 */
$('.comment-like').click(function () {
    const a = $(this);
    const commentId = $(this).attr('data-id');

    $.ajax({
        type: 'POST',
        url: '/forum/like',
        async: false,
        data: {
            'entityType': 2,
            'entityId':commentId
        },
        success: function (data) {
            if (data.code == 1) {
                a.find('.tt-text').text(data.result.likeCount);
                a.find('.tt-icon').attr('style', data.result.likeStatus==1?'color:blue':'color:green');
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 评论点踩
 */
$('.comment-dislike').click(function () {
    const a = $(this);
    const commentId = $(this).attr('data-id');
    const item = localStorage.getItem("comment-dislike-" + commentId);
    if (item != null) {
        showMsg('您已经点过踩了！', "info", 1000);
        return;
    }
    $.ajax({
        type: 'POST',
        url: '/comment/dislike',
        async: false,
        data: {
            'commentId': commentId
        },
        success: function (data) {
            if (data.code == 1) {
                const count = parseInt(a.find('.tt-text').text()) + 1;
                a.find('.tt-text').text(count);
                a.attr('style', 'color: #2172cda;pointer-events: none;');
                localStorage.setItem("comment-dislike-" + commentId, count);
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});


/**
 * 文章点赞
 */
$('.post-like').click(function () {
    const a = $(this);
    const postId = $(this).attr('data-id');
    $.ajax({
        type: 'POST',
        url: '/forum/like',
        async: false,
        data: {
            'entityType': 1,
            'entityId': postId
        },
        success: function (data) {
            if (data.code == 1) {
                a.find('.tt-text').text(data.result.likeCount);
                a.find('.tt-icon').attr('style', data.result.likeStatus==1?'color:blue':'color:green');
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 关注
 */
$('.follow-btn').click(function () {
    const entityId = $(this).attr('data-id');
    $.ajax({
        type: 'POST',
        url: '/forum/follow',
        async: false,
        data: {
            'entityType': 3,
            'entityId': entityId
        },
        success: function (data) {
            if (data.code == 1) {
//                $(this).parentNode.find(".follow-btn").attr('style', 'display:none;');
                window.location.reload();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 取关
 */
$('.unfollow-btn').click(function () {
    const entityId = $(this).attr('data-id');
    $.ajax({
        type: 'POST',
        url: '/forum/unfollow',
        async: false,
        data: {
            'entityType': 3,
            'entityId': entityId
        },
        success: function (data) {
            if (data.code == 1) {
//                $(this).parentNode.find(".unfollow-btn").attr('style', 'display:none;');
                window.location.reload();
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
});

/**
 * 加载未读数量
 */
function loadNotReadCommentCount() {
    $.ajax({
        type: 'GET',
        url: '/comment/notReadCount',
        async: false,
        success: function (data) {
            if (data.code == 1) {
                const count = data.result;
                if (count != 0) {
                    $('#notReadCount').text(count);
                }
            } else {
                showMsg(data.msg, "error", 1000);
            }
        }
    });
}
loadNotReadCommentCount();



