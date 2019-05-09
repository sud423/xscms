/**
 * 焦点事件js
 */

setMenuCls("course");// 设置导航栏样式

$(function() {
//	$.fn.datetimepicker.dates['en'] = {
//		days : [ "日", "一", "二", "三", "四", "五", "六","日" ],
//		daysShort : [ "日", "一", "二", "三", "四", "五", "六","日" ],
//		daysMin : [ "日", "一", "二", "三", "四", "五", "六","日" ],
//		months : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
//		monthsShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
//		today : "今天"
//	};
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbCourse').DataTable({
			"ajax" : {
				url : basePath + "/course/list",
				type : 'post',
				data : function(d) {
					d.keyword = $("#txtKeyword").val();
					d.isSys = true;
				}
			},
			"columns" : [ {
				"data" : "id",
				"render" : function(data, type, row, meta) {
					var seqNo = meta.settings._iDisplayStart + meta.row + 1;
					return seqNo;
				}
			}, {
				"data" : "academy"
			}, {
				"data" : "classify"
			}, {
				"data" : "name"
			}, {
				"data" : "fee"
			}, {
				"data" : "address"
			}, {
				"data" : "openDate"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 7,
				"visible" : visible
			} ],
			drawCallback : function() {
				$('[data-toggle="popover"]').each(function() {
					var element = $(this);
					element.popover().on("click", function() {
						clspop();
						var _this = $(this);
						setTimeout(function() {
							_this.popover("show");
						}, 100);
					});
				});
			}
		});

		// 查询
		$("#btnCourseSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {

		$("input[name='sort']").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});
		$("input[name='fee']").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999,
			decimals : 2,
			step : 0.01
		});
		$(".form_datetime").datetimepicker({
			dateFormat : 'yyyy-mm-dd HH:ii',
			autoclose : true,
			startDate : new Date(),
			locale: moment.locale('zh-cn')
		});
		$("#courseForm").validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#courseForm button").prop("disabled", true);
				$.post(basePath + "/course/save", $("#courseForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/course/index";
						});
					}
				}).always(function() {
					$("#courseForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/course/edit/' + id;
}

/**
 * 删除事件
 * 
 * @param id
 *            事件编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/course/change?courseId=' + id, function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : data.result,
				type : "success"
			}, function() {
				clspop();
				window.table.ajax.reload();
			});
		}
	})
}