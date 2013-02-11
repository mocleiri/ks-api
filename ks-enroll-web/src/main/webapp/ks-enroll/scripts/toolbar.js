function handleToolbar(divId, hiddenColumns, bottons){
    var hiddenColumnsMap = new Object();
    var buttonsMap = new Object();
    for(var j=0; j<hiddenColumns.length; j++) {
        var field = getSelector(hiddenColumns[j]);
        hiddenColumnsMap[field]=[];
        buttonsMap[field]=bottons[j];
    }

    processToolbar(divId, hiddenColumnsMap, buttonsMap);
}

function processToolbar(divId, hiddenColumnsMap, buttonsMap){
    var oTable = getDataTableHandle(divId);

    var selectedRows =  jQuery(oTable.fnGetNodes()).filter(':has(:input:checkbox:checked)');

   jQuery(selectedRows).each(function(){
       var cellData = oTable.fnGetData(this);
       for(var i=0; i<cellData.length; i++)
       {
           var celld=cellData[i];
           for(var key in hiddenColumnsMap){
               var col = hiddenColumnsMap[key];
               var val = jQuery(cellData[i]).find(key).val();
               if(jQuery(cellData[i]).find(key).val() == 'true' || jQuery(cellData[i]).find(key).val() == 'false'){
                   hiddenColumnsMap[key].push(jQuery(cellData[i]).find(key).val());
               }
           }
       }
   });

   handleButtons(hiddenColumnsMap, buttonsMap);
}

function getSelector(name){
    return "input[name$='" + name +"']";
}

function getDataTableHandle(divId){
    var id = jQuery("#" + divId).find("table").attr('id');
    var oTable = jQuery("#" + id).dataTable();
    return oTable;
}

function handleButtons(hiddenColumnsMap, buttonsMap) {
   for(var key in hiddenColumnsMap)
   {
       handleButton(hiddenColumnsMap[key], buttonsMap[key]);
   }
}

function handleButton(arr, id) {
    if(jQuery.isEmptyObject(arr)){
        disableButton(id);
    } else{
        evaluateButton(arr, id);
    }
}

function evaluateButton(arr, id){
    if(jQuery.inArray("true", arr) != -1){
        enableButton(id);
    } else{
        disableButton(id);
    }
}

function enableButton(id){
    jQuery("#" + id).removeClass('disabled');
    jQuery("#" + id).removeAttr("disabled");
    var img = jQuery("#" + id).find('img') ,
           src = img.attr('src') ,
           disable = /\_disabled\.png$/;

    if(src.match(disable)) {
        img.attr('src', src.replace(disable, '_enabled.png'));
    }

}

function disableButton(id){
    jQuery("#" + id).addClass("disabled");
    jQuery("#" + id).attr("disabled", "disabled");
    var img = jQuery("#" + id).find('img') ,
           src = img.attr('src') ,
           enable = /\_enabled\.png$/ ;

    if(src.match(enable)) {
        img.attr('src', src.replace(enable, '_disabled.png'));
    }
}
