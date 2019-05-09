/**
 * 项目管理
 */

setMenuCls("training_cla");// 设置导航栏样式


$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbCla').DataTable({
			"ajax" : {
				url : basePath + "/cla/list",
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
				"data" : "pName"
			}, {
				"data" : "name"
			}, {
				"data" : "remark",
				"render":function(data,type,row,meta){
					if(!data)
						return data;
					var s=data.substring(0,60);
					if(data.length>60)
						s+="..."
					return "<span title='"+data+"'>"+s+"</span>";
				}
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
		$("#btnClaSearch").bind("click", function() { // 点击按钮
			// 触发table重新请求服务器
			window.table.ajax.reload();
		});


	} else {
		$("select[name='parentId']").select2({
			language : 'zh-CN',
			allowClear : true,
			ajax : {
				url : basePath + '/project/getall',
				dataType : 'json',
				quietMillis : 250,
				data : function(params) {
					return {
						q : params.term, // search term
					};
				},
				processResults : function(data, page) {
					return {
						results : data
					};
				},
				cache : true
			}
		});
		// 编辑页面信息保存
		$("#claForm").validator().on('submit', function(e) {

			if (e.isDefaultPrevented()) {
				// 处理无效的表单...
				// swal("信息", "车牌不能为空",
				// "error");
				return false;
			} else {
				$("#claForm button").prop("disabled", true);
				$.post(basePath + "/cla/save", $("#claForm").serialize(), function(data, status) {
					if (data.code != 0) {
						swal("信息", data.result, "error");
					} else {
						swal({
							title : "信息",
							text : data.result,
							type : "success"
						}, function() {
							window.location.href = basePath + "/cla/index";
						});
					}
				}).always(function() {
					$("#claForm button").prop("disabled", false);
				});
				return false;
			}
		});

	}

});

function redirect(id) {
	window.location.href = basePath + '/cla/edit/' + id;
}

/**
 * 删除车辆
 * 
 * @param id
 *            车辆编号
 * @param opType
 *            1：空闲 10：使用中 20：报废
 * @returns
 */
function change(id, type) {
	$.post(basePath + '/cla/change?id=' + id, function(data) {
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
