function showButton(){	
	var num = $('input:checkbox:checked').length;
	
    if ( num == 0 ) {
    	$('#btnExcluir').hide();
    } else {
    	$('#btnExcluir').show();
    }
}