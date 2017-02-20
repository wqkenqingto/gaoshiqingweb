window._tplObj={};
window._renderObj={};
window._htmlObj={};
var type;
var cityArr = [];

function aa(){
	_tplObj.itemList=$('#city_tpl').html();
	_renderObj.itemList=_.template(_tplObj.itemList);
	_htmlObj.itemList=_renderObj.itemList(cityInfo);
	$('.selectOption').html(_htmlObj.itemList);
	check();
};

$(document).on("click",".wrap",function(){
	$(this).parent().append('<div class="city-popup">'+
	'<div class="selectedArea">'+
	'</div>'+
	'<a class="sure" id="city-sure">确定</a>'+
	'<div class="selectOption"></div>'+
	'</div>');
	$('.wrap').append('<div class="mask-wrap"></div>');
	aa();
	$('.city-popup').show();
	cityArr = [];
	
	var codes = $(this).prev().eq(0).val().split(",");
	for (var i = 0; i < codes.length; i++) {
		if (codes[i] != '') {
			$("[data-id="+codes[i]+"]").trigger("click");
		}
	}
})

$(document).on("click","#city-sure",function(){
	var put = $(this).parent().prev().eq(0).attr('id');
	$('.city-popup').hide();
	$('.mask-wrap').remove();
	pushdata(put);
	$('.city-popup').remove();
})

function check(){
	$('.province.city-block').on('click',function(){
		if ($(this).attr("data-add") == 1){
			var num = $(this).attr("data-num");
			var code = $(this).attr("data-id");
			$('.city').each(function(){
				if ($(this).attr('data-num') == num) {
					$(this).removeClass('on');
					$(this).attr("data-add",'1');
				}
			})
			$('.selected.city').each(function(){
				if ($(this).attr('data-num') == num) {
					$(this).remove();
				}
			})
			type = 'province';
		add(this);
		};
	});
	$('.city.city-block').on('click',function(){
		if ($(this).attr("data-add") == 1){
			var num = $(this).attr("data-num");
			$('.province').each(function(){
				if ($(this).attr('data-num') == num) {
					$(this).removeClass('on');
					$(this).attr("data-add",'1');
				}
			})
			$('.selected.province').each(function(){
				if ($(this).attr('data-num') == num) {
					$(this).remove();
				};
			})
			type = 'city';
		add(this);
		};
	});
}

function add(obj){
	$(obj).addClass("on");
	$(obj).attr("data-add",'0');
	var code = $(obj).attr("data-id");
	var num = $(obj).attr("data-num");
	var text = $(obj).attr("data-text");
	$('.selectedArea').append('<span class="selected '+type+'" data-id="'+code+'" data-num="'+num+'">'+text+'</span>');
	del();
}

function del(){
	$('.selected').on('click',function(){
		var code = $(this).attr("data-id");
		$(this).remove();
		$('.city-block').each(function(){
			if ($(this).attr('data-id') == code) {
				$(this).removeClass('on');
				$(this).attr("data-add",'1');
			}
		})
	});
}

function pushdata(put){
	$('.selected').each(function(){
		var code = $(this).attr('data-id');
		var name = $(this).html();
		cityArr.push({'code':code,'name':name});
	});
	var names="";   
	var codes=""; 
	for (var i = 0; i < cityArr.length; i++) {
		if (i != cityArr.length-1) {
			names += cityArr[i].name+" "; 
		    codes += cityArr[i].code+","; 
		} else {
			names += cityArr[i].name; 
		    codes += cityArr[i].code; 
		}
	}
    $('#'+put).val(names);
    $('#'+put).prev().eq(0).val(codes);
}