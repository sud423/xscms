/**
 * 活动管理js
 */

setMenuCls("activity");// 设置导航栏样式

$(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbAnnounce').DataTable({
			"ajax" : {
				url : basePath + "/activity/list",
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
				"data" : "title"
			}, {
				"data" : "status",
				"render" : function(data, type, full, callback) {
					switch (data) {
					case 1:
						return "发布中";
						break;
					case 10:
						return "待发布 ";
						break;
					case 30:
						return "已过期 ";
						break;
					}
				}
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 4,
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
		$("#btnActivitySearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});
	} else {
		$('.input-daterange-datepicker').daterangepicker({
			minDate : moment(),
			autoUpdateInput : false,
			timePicker : true, // 显示时间
			timePicker24Hour : true, // 时间制
			locale : {
				cancelLabel : '清除',
				applyLabel : "确定",
				daysOfWeek : [ "日", "一", "二", "三", "四", "五", "六" ],
				monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
				firstDay : 1
			}
		}, function(start, end, label) {
			$('.input-daterange-datepicker').val(start.format('YYYY/DD/MM HH:mm') + ' - ' + end.format('YYYY/DD/MM HH:mm'));
			$("input[name='beginTime']").val(start.format('YYYY-DD-MM HH:mm'));
			$("input[name='endTime']").val(end.format('YYYY-DD-MM HH:mm'));

			// console.log('New date range selected: ' +
			// start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD') +
			// ' (predefined range: ' + label + ')');
		});

		// $('.input-daterange-datepicker').on('apply.daterangepicker',
		// function(ev, picker) {
		// $("input[name='beginTime']").val(picker.startDate.format('YYYY-DD-MM'));
		// $("input[name='endTime']").val(picker.endDate.format('YYYY-DD-MM'));
		// $(this).val(picker.startDate.format('YYYY/DD/MM') + ' - ' +
		// picker.endDate.format('YYYY/DD/MM'));
		// });
		//
		$('.input-daterange-datepicker').on('cancel.daterangepicker', function(ev, picker) {
			$(this).val('-');
			$("input[name='beginTime']").val("");
			$("input[name='endTime']").val("");
		});

		$(".vertical-spin").TouchSpin({
			verticalbuttons : true,
			verticalupclass : 'ti-plus',
			verticaldownclass : 'ti-minus',
			min : 0,
			max : 99999999
		});

		$("#activityForm").validator().on('submit', function(e) {
			if (e.isDefaultPrevented()) {
				return false;
			} else {
				$("#activityForm button").prop("disabled", true);
				$.post(basePath + "/activity/save", $("#activityForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/activity/index";
						});
					}
				}).always(function() {
					$("#activityForm button").prop("disabled", false);
				});
				return false;
			}
		});
	}
});

function redirect(id) {
	window.location.href = basePath + '/activity/edit/' + id;
}

/**
 * 删除车辆
 * 
 * @param id
 *            活动编号
 * @returns
 */
function change(id) {
	$.post(basePath + '/activity/change?activityId=' + id, function(data) {
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