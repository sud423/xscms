/**
 * 客户管理js
 */
setMenuCls("client");// 设置导航栏样式

$(document).ready(function() {
	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbUser').DataTable({
			"ajax" : {
				url : basePath + "/client/list",
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
				"data" : "name"
			}, {
				"data" : "cell"
			}, {
				"data" : "nickName"
			}, {
				"data" : "idNumber"
			}, {
				"data" : "attach",
				"render" : function(data, type, row) {
					var html = []
					if (data) {
						for (var i = 0; i < data.length; i++) {
							html.push("<img src='" + data[i] + "' width='80' height='50' style='margin-left:10px;' />");
						}
					}
					return html.join("");
				}
			}, {
				"data" : "status"
			}, {
				"data" : "addTime"
			} ],
			"columnDefs" : [ {
				"render" : function(data, type, row) {
					switch (data) {
					case 1:
						return "正常";
					case 2:
						return "待审核";
					case 10:
						return "冻结";
					case 30:
						return "审核不通过";
					default:
						return "";
					}
				},
				"targets" : 6
			}, {
				"render" : opt,
				"targets" : 8,
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
				$("img").each(function(){
					var self=$(this);
//					var items=new Array(self,$(self.siblings()));
					
					self.on("click",function(){
						var items = [
						    {
						        src: self.attr("src"),
						        w: self.get(0).naturalWidth,
						        h: self.get(0).naturalHeight
						    },
						    {
						        src: $(self.siblings()).attr("src"),
						        w: $(self.siblings()).get(0).naturalWidth,
						        h: $(self.siblings()).get(0).naturalHeight
						    }
						];
						showPhoto(items);
					});
				})
			}
		});
	}

	// 查询
	$("#searchClients").bind("click", function() {
		// 点击按钮
		// 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 对话框关闭事件
	$("#approval").on('show.bs.modal', function() {
		auditForm.reset();
	});

	$(".radio-list input[type='radio']").change(function() {
		if ($(this).val() == 30) {
			$("#reason_box").show();
		} else {
			$("#reason_box").hide();
		}
	});
});

/**
 * 打开审核客户信息窗口
 * 
 * @param id
 * @returns
 */
function openAuditWin(id) {
	$("#approval").modal();
	$("#txtUserId").val(id);
}

/**
 * 审核客户信息
 * 
 * @returns
 */
function audit() {
	if (!$("#unpass").prop("checked") && !$("#pass").prop("checked")) {
		swal("信息", "请选择审核结果", "error");
		return;
	}
	if ($("#unpass").prop("checked") && !$("#txtReason").val()) {
		swal("信息", "审核通过不原因不能为空", "error");
		return;
	}
	$(".modal-footer button").prop("disabled", true);
	$.post(basePath + '/client/audit', $("#auditForm").serialize(), function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : data.result,
				type : "success"
			}, function() {
				$("#approval").modal('hide');
				window.table.ajax.reload();
			});
		}
	}).always(function() {
		$(".modal-footer button").prop("disabled", false);
	});
}

/**
 * 启用/冻结
 * 
 * @param id
 *            用户编号
 * @param opType
 *            1：启用 10：冻结
 * @returns
 */
function change(id, opType) {
	$.post(basePath + '/sysuser/change', {
		userId : id,
		opType : opType
	}, function(data) {
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