var count = 0;
var ponumber = 0;
$(document).ready(function()
{  
  var rootURL = "http://ec2-54-200-141-55.us-west-2.compute.amazonaws.com:8080/Info5059Case2-war/webresources/vendors/vendorlist";      
 $.getJSON(rootURL ,null, function(data,status,jqXHR) {
           renderVendorInfo(data);
  }).error(function(jqXHR, textStatus, errorThrown){
            console.log(textStatus + " - " + errorThrown);
 });
 
 $("#poGrid").hide();
 $("#poset").hide();
});

function renderVendorInfo(data) {
    for(var i = 0; i < data.length; ++i)
    {         
        $("<option/>").attr("value", data[i]).text(data[i]).appendTo($("#vendorno"));
    }    
}

function renderPOInfo(data) {
    
    $("#ponos").empty();
    for(var i = 0; i < data.length; ++i)
    {         
        $("<option/>").attr("value", data[i].ponumber).text(data[i].ponumber).appendTo($("#ponos"));
    }    
}
function renderPODetails(data1){
    var stuff;    
    var sub = 0.0;
    var tax = 0.0;
    var total = 0.0;
    var productPrice = 0.0;
    var productExt = 0.0;
    var prodqty = 0;
    for(var i = 0; i < data1.length; i++)
        {
            if(data1[i].ponumber == ponumber)
                {
                    for( var t = 0; t < data1[i].items.length; t++)
                        {
                            productPrice = data1[i].items[t].price;
                            productExt = data1[i].items[t].ext;
                            var anotherf = '<div class="ui-grid-d"><div class="ui-block-a"><center>' + data1[i].items[t].prodcd + '</center></div><div class="ui-block-b"><center>' + data1[i].items[t].prodname +'</center></div><div class="ui-block-c" style="text-align:right;">$' + productPrice.toFixed(2) + '</div><div class="ui-block-d"><center>'+ data1[i].items[t].qty + '</center></div><div class="ui-block-e" style="text-align:right;">$' + productExt.toFixed(2) + '</div>';
                            $(collapse).append(anotherf);
                            gg = data1[i].items[t].price;
                            prodqty = data1[i].items[t].qty;
                            sub += (gg * prodqty);
                        }
                        tax = sub * 0.13;
                        total = data1[i].total;
                    $(collapse).append('<div class="ui-grid-d"><div class="ui-block-a"><center></center></div><div class="ui-block-b"><center></center></div><div class="ui-block-c"><center></center></div><div class="ui-block-d"><center></center></div><div class="ui-block-e" style="text-align:right;">-----------</div>');
                    $(collapse).append('<div class="ui-grid-d"><div class="ui-block-a"><center></center></div><div class="ui-block-b"><center></center></div><div class="ui-block-c"><center></center></div><div class="ui-block-d"><center>Sub</center></div><div class="ui-block-e" style="text-align:right;">$' + sub.toFixed(2) + '</div>');
                    $(collapse).append('<div class="ui-grid-d"><div class="ui-block-a"><center></center></div><div class="ui-block-b"><center></center></div><div class="ui-block-c"><center></center></div><div class="ui-block-d"><center>Tax</center></div><div class="ui-block-e" style="text-align:right;">$' + tax.toFixed(2) + '</div>');
                    $(collapse).append('<div class="ui-grid-d"><div class="ui-block-a"><center></center></div><div class="ui-block-b"><center></center></div><div class="ui-block-c"><center></center></div><div class="ui-block-d"><center>Total</center></div><div class="ui-block-e" style="text-align:right;background-color:yellow;">$' + total.toFixed(2)+ '</div>');
                }
        }
        $(collapse).appendTo($("#poset"));
         $(collapse).collapsible({refresh : true});
    var ttt = data1[0].items.length;
    return stuff;
}

function renderPO(data) {
    var h3;
    var t = data[0].vendorno.vendorno;
    ponumber = data[0].ponumber;
    var date = new Date(data[0].podate);
    var r; 
    var ffff;
    
     var stuff;
    if(count < 1)
        {         
         var h3 = $("<h3 id='head11'> PO# " + data[0].ponumber + " - " + date.toDateString() + "</h3> ");
         $(collapse).append(h3);
         var productHeading = $('<div style="background-color:black;"><center>Products</center></div>');
         $(collapse).append(productHeading);
         var productHeadings = $('<div class="ui-grid-d" style="background-color:black;"><div class="ui-block-a"><center>Code</center></div><div class="ui-block-b"><center>Name</center></div><div class="ui-block-c"><center>Price</center></div><div class="ui-block-d"><center>Qty</center></div><div class="ui-block-e"><center>Ext.</center>');
         $(collapse).append(productHeadings);
         var rootURL1 = "http://ec2-54-200-141-55.us-west-2.compute.amazonaws.com:8080/Info5059Case2-war/webresources/vendors/";
         $.getJSON(rootURL1 + "APOS/" + t ,null, function(data1,status,jqXHR) {
                     
    
                    ffff = renderPODetails(data1);
                      
             }).error(function(jqXHR, textStatus, errorThrown){
                       console.log(textStatus + " - " + errorThrown);
            });
            
         
         count++;
        }
        else
        {
          var h3 = $('#head11').text("PO# " + data[0].ponumber + " - " + date.toDateString());
          $(collapse).append(h3);
          var productHeading = $('<div style="background-color:black;"><center>Products</center></div>');
         $(collapse).append(productHeading);
         var productHeadings = $('<div class="ui-grid-d" style="background-color:black;"><div class="ui-block-a"><center>Code</center></div><div class="ui-block-b"><center>Name</center></div><div class="ui-block-c"><center>Price</center></div><div class="ui-block-d"><center>Qty</center></div><div class="ui-block-e"><center>Ext.</center>');
         $(collapse).append(productHeadings);
        var rootURL1 = "http://ec2-54-200-141-55.us-west-2.compute.amazonaws.com:8080/Info5059Case2-war/webresources/vendors/";
         $.getJSON(rootURL1 + "APOS/" + t ,null, function(data1,status,jqXHR) {
                     
    
                    ffff = renderPODetails(data1);
                      
             }).error(function(jqXHR, textStatus, errorThrown){
                       console.log(textStatus + " - " + errorThrown);
            });
        }
        
        
        
    
}

$(function(){   
    //event handler for vendor# click
   $('#choosePO').click(function(){
       collapse = $("");
       $(collapse).appendTo($("#poset"));
        $("#poset").show();
         collapse = $("<div id='test1'>").attr({
             'data-role' : "collapsible",
             'id' : "container"
         });
         var test = $('#ponos').val();
         var rootURL = "http://ec2-54-200-141-55.us-west-2.compute.amazonaws.com:8080/Info5059Case2-war/webresources/vendors/";
         $.getJSON(rootURL + "getAPO/" + test,null, function(data,status,jqXHR) {
                      renderPO(data);
                      
                      
             }).error(function(jqXHR, textStatus, errorThrown){
                       console.log(textStatus + " - " + errorThrown);
            });        
    });
     
});

$(function(){   
    //event handler for vendor# click
    $('#chooseVendor').click(function(){
        $("#poGrid").show();
        var rootURL = "http://ec2-54-200-141-55.us-west-2.compute.amazonaws.com:8080/Info5059Case2-war/webresources/vendors/";      
           $.getJSON(rootURL + "getPos/" + $('#vendorno').val(),null, function(data,status,jqXHR) {
                      renderPOInfo(data);
             }).error(function(jqXHR, textStatus, errorThrown){
                       console.log(textStatus + " - " + errorThrown);
            });
    });
     
});
