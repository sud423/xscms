/**
 * 用户管理js
 */
setMenuCls("resource");// 设置导航栏样式

$(document).ready(function() {

	var url = window.location.href;
	// 判断是否为列表页，如果是就加载数据
	if (url.toLowerCase().indexOf("index") > -1) {
		window.table = $('#tbResource').DataTable({
			"ajax" : {
				url : basePath + "/resource/list",
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
				"data" : "type"
			}, {
				"data" : "permission"
			}, {
				"data" : "sort"
			}, {
				"data" : "remark"
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
					case 10:
						return "禁用";
					default:
						return "";

					}
				},
				"targets" : 6
			}, {
				"render" : opt,
				"targets" : 8,
				"visible" : visible
			} ]
		});

	} else {
		init();
	}

	// 查询
	$("#btnResource").bind("click", function() { // 点击按钮 触发table重新请求服务器
		window.table.ajax.reload();
	});

	// 编辑页面信息保存
	$("#resourceForm").validator().on('submit', function(e) {

		if (e.isDefaultPrevented()) {
			// 处理无效的表单...
			// swal("信息", "用户名和密码不能为空", "error");
			return false;
		} else {
			$("#resourceForm button").prop("disabled", true);
			$.post(basePath + "/resource/save", $("#resourceForm").serialize(), function(data) {
				if (data.code != 0) {
					swal("信息", data.result, "error");
				} else {
					swal({
						title : "信息",
						text : data.result,
						type : "success"
					}, function() {
						window.location.href = basePath + "/resource/index";
					});
				}

			}).fail(function(e, s, j, d) {
				if (e.status == 404)
					swal("信息", "请求的路径不存在", s);
				else
					swal({title:"信息", text:e.responseJSON.result, type:s},function(){
						if(e.responseJSON.code=="500403")
							window.location.href=basePath+"/login"
					});
			}).always(function(e) {
				$("#resourceForm button").prop("disabled", false);
			});
			return false;
		}
	})
});

/**
 * 连接跳转到编辑页面
 * 
 * @param id
 *            用户编号
 * @returns
 */
function redirect(id) {
	window.location.href = basePath + '/resource/edit/' + id;
}

/**
 * 重置密码
 * 
 * @param id
 *            用户编号
 * @returns
 */
function resetPwd(id) {
	$.post(basePath + '/sysuser/reset', {
		userId : id
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
			});
		}
	})
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

function init() {
	var setting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClick
		}
	};

	$.fn.zTree.init($("#treeDemo"), setting, zNodes);

}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), v = "", ids = "";
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		ids += nodes[i].id + ",";
	}
	if (v.length > 0) {
		v = v.substring(0, v.length - 1);
		ids = ids.substring(0, ids.length - 1);
	}
	$("#txtTree").val(v);
	$("#pId").val(ids);
	hideMenu();
}

function showMenu() {
	var cityObj = $("#txtTree");
	var cityOffset = $("#txtTree").offset();
	$("#menuContent").css({
		left : cityOffset.left + "px",
		top : cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}