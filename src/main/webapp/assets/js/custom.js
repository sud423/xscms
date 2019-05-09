$(document).ready(function() {
	$(function() {
		$(".preloader").fadeOut();
		$('#side-menu').metisMenu();
	});
	
	//在窗口加载时加载正确的侧边栏，在窗口大小调整时折叠侧边栏。
	//将#page-wrapper的最小高度设置为窗口大小
	$(window).bind("load resize", function () {
        topOffset = 60;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        }
        else {
            $('div.navbar-collapse').removeClass('collapse');
        }
        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });
    // 调整窗口大小
	 $(window).bind("load resize", function () {
         width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
         if (width < 1170) {
             $('body').addClass('content-wrapper');
             $(".sidebar-nav, .slimScrollDiv").css("overflow-x", "visible").parent().css("overflow", "visible");
             
         }
         else {
             $('body').removeClass('content-wrapper');
             
         }
     });
    
   
  //修改密码
  $(function () {
      $("#pwdform").validator().on('submit', function (e) {
          if (e.isDefaultPrevented()) {
              // 处理无效的表单...
              //swal("信息", "用户名和密码不能为空", "error");
              return false;
          } else {
              $("#pwdform button").prop("disabled", true);
              $.post(basePath+"/sysuser/chgPwd",
                  $("#pwdform").serialize(),
                  function (data, status) {
                      if (data.code==0){
                    	  swal({title : "信息",
								text : "密码修改成功，请重新登录进行身份验证",
								type : "success"
							},function() {
								window.location.href=basePath+"/login";
							})
                      }
                      else {
                          swal("信息", data.result, "error");
                      }
                  }).always(function() {
      				$("#pwdform button").prop("disabled",false);
      			});
              return false;
          }
      })
  });
});

$('.slimscrollsidebar').slimScroll({
    height: '100%'
  , position: 'left'
  , size: "6px"
  , color: 'rgba(0,0,0,0.5)'
, });


//设置菜单选中样式
function setMenuCls(id) {

    $("#" + id).addClass("active").parent().parent().addClass('in').siblings("a").addClass("active").parent('li').addClass("active");
 
}

//获取字符串长度
function getlen(str) {
  ///<summary>获得字符串实际长度，中文2，英文1</summary>
  ///<param name="str">要获得长度的字符串</param>
  if (!str)
      return 0;
  var realLength = 0, len = str.length, charCode = -1;
  for (var i = 0; i < len; i++) {
      charCode = str.charCodeAt(i);
      if (charCode >= 0 && charCode <= 128) realLength += 1;
      else realLength += 2;
  }
  return realLength;
};

//js截取字符串，中英文都能用  
//如果给定的字符串大于指定长度，截取指定长度返回，否者返回源字符串。  
//字符串，长度  

/** 
* js截取字符串，中英文都能用 
* @param str：需要截取的字符串 
* @param len: 需要截取的长度 
*/
function cutstr(str, len) {
  var str_length = 0;
  var str_len = 0;
  str_cut = new String();
  str_len = str.length;
  for (var i = 0; i < str_len; i++) {
      a = str.charAt(i);
      str_length++;
      if (escape(a).length > 4) {
          //中文字符的长度经编码之后大于4  
          str_length++;
      }
      str_cut = str_cut.concat(a);
      if (str_length >= len) {
          str_cut = str_cut.concat("...");
          return str_cut;
      }
  }
  //如果给定字符串小于指定长度，则返回源字符串；  
  if (str_length < len) {
      return str;
  }
}

/**
 * 关闭汔泡弹框
 * @returns
 */
function clspop(){
	$(".popover").popover('hide');
}


function showPhoto(items){
	var pswpElement = document.querySelectorAll('.pswp')[0];
	console.log(items);
		// build items array
//		var items = [
//		    {
//		        src: items[0].attr("src"),
//		        w: items[0].get(0).naturalWidth,
//		        h: items[0].get(0).naturalHeight
//		    },
//		    {
//		        src: items[1].attr("src"),
//		        w: items[0].get(0).naturalWidth,
//		        h: items[0].get(0).naturalHeight
//		    }
//		];

		// define options (if needed)
		var options = {
		    // optionName: 'option value'
		    // for example:
		    index: 0 // start at first slide
		};

		// Initializes and opens PhotoSwipe
		var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
		gallery.init();
}

/**
 * jQuery naturalWidth() and naturalHeight()
 * @param $
 * @returns
 */
(function($){
    var
    props = ['Width', 'Height'],
    prop;

    while (prop = props.pop()) {
    (function (natural, prop) {
      $.fn[natural] = (natural in new Image()) ? 
      function () {
      return this[0][natural];
      } : 
      function () {
      var 
      node = this[0],
      img,
      value;

      if (node.tagName.toLowerCase() === 'img') {
        img = new Image();
        img.src = node.src,
        value = img[prop];
      }
      return value;
      };
    }('natural' + prop, prop.toLowerCase()));
    }
  }(jQuery));
