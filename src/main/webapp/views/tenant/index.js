/**
 * 用户管理js
 */
setMenuCls("tenant");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbTenant').DataTable({
			"ajax" : {
				url : basePath + "/tenant/list",
				type : 'post',
				data : function(d) {
					d.keyword = $("#txtTenantKeyword").val();
					d.isSys = true;
				}
			},
			"bPaginate" : false,
			"columns" : [ {
				"data" : "id",
				"render" : function(data, type, row, meta) {
					var seqNo = meta.settings._iDisplayStart + meta.row + 1;
					return seqNo;
				}
			}, {
				"data" : "name"
			}, {
				"data" : "company"
			}, {
				"data" : "contact"
			}, {
				"data" : "cell"
			}, {
				"data" : "domain"
			}, {
				"data" : "ip",
				"render":function (data,type,row) {
					if(data && row.port)
						return data+":"+row.port;
					else
						return data;
				}
			}, {
				"data" : "account"
			} , {
				"data" : "password"
			} , {
				"data" : "os"
			}  ],
			"columnDefs" : [ {
				"render" : opt,
				"targets" : 10,
				"visible" : visible
			} ]
		});

	}

	// 查询
	$("#searchTenant").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	$('#responsive-modal').on('show.bs.modal', function() {
		var zTree = $.fn.zTree.init($("#treeDemo"), {
			check : {
				enable : true
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onCheck : onCheck
			}
		}, zNodes);
		setCheck();
		zTree.expandAll(true);
	});

	// 编辑页面信息保存
	$("#tenantForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#tenantForm button").prop("disabled", true);
			$.post(basePath + "/tenant/save", $("#tenantForm").serialize(), function(data, status) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/tenant/index";
					});
				}
			}).always(function() {
				$("#tenantForm button").prop("disabled", false);
			});
			return false;
		}
	});

	$('#responsive-modal').on('hide.bs.modal', function() {
		$('#responsive-modal input[type="checkbox"]').prop("checked", false)
	});
});

/**
 * 连接跳转到编辑页面
 * 
 * @param id
 *            用户编号
 * @returns
 */
function redirect(id) {
	window.location.href = basePath + '/tenant/edit/' + id;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getCheckedNodes(), v = [];
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v.push(nodes[i].id);
	}

	$("#txtResouceIds").val(v.join(','));
}


function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),

		type = {
			"Y" : "ps",
			"N" : "ps"
		};
	zTree.setting.check.chkboxType = type;
}

function openWin(id, resouces) {

	$("#txtTenantId").val(id);

	$("#responsive-modal").modal();
	if (arguments.length > 1) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		$.each(arguments, function(index) {
			if (typeof this != "function" && index > 0 && this) {
				var node = zTree.getNodeByParam("id", this);
				if (node != null) {
					zTree.checkNode(node, true)
				}
			}
		});
	}
}

/**
 * 保存角色 配置
 * 
 * @returns
 */
function saveResource() {
	$(".modal-footer button").prop("disabled", true);
	$.post(basePath + "/tenant/saveresource", $("#resourceForm").serialize(), function(data) {
		if (data.code != 0) {
			swal("信息", data.result, "error");
		} else {
			swal({
				title : "信息",
				text : "配置成功",
				type : "success"
			}, function() {
				$("#responsive-modal").modal('hide');
				window.table.ajax.reload();
			});
		}
	}).always(function() {
		$(".modal-footer button").prop("disabled", false);
	});
}
