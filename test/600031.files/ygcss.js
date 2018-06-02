<!--
ygagt=navigator.userAgent.toLowerCase();ygd=document;ygdom=(document.getElementById)?1:0;ygns=(ygd.layers)?1:0;ygns6=(ygdom&&navigator.appName=="Netscape");ygie=(ygd.all);ygwin=((ygagt.indexOf("win")!=-1)||(ygagt.indexOf("16bit")!=-1));ygmac=(ygagt.indexOf("mac")!=-1);ygnix=((ygagt.indexOf("x11")!=-1)||(ygagt.indexOf("linux")!=-1));
var ygar = new Array();
ygar[0]="<style type=\"text/css\">\n";
// keywords: NN4 PC - 96dpi
if (ygns && ygwin){
ygar[1]="th,td{font-size:9pt;}";
ygar[2]="input{font-family:monospace;font-size:12px;}";
ygar[3]="big{font-size:10pt;line-height:1.5;}";
ygar[4]="p{font-size:12pt;line-height:1.5;}";
ygar[5]="small{font-size:9pt;}";}
// keywords: NN4 Mac - 72dpi
else if (ygns && ygmac){
ygar[1]="body,th,td{font-size:medium;}";
ygar[2]="big{font-size:big;}";
ygar[3]="p{font-size:medium;}";
ygar[4]="small{font-size:small;}";}
// keywords: NN4 *nix - 72dpi
else if (ygns && ygnix){
ygar[1]="body,th,td{font-size:small;}";
ygar[2]="input,select{line-height:7px;font-family:monospace;font-size:small;}";
ygar[3]="big{font-size:110%;}";
ygar[4]="p{font-size:medium;}";
ygar[5]="small{font-size:small;}";}
// pixels: NN6 PC/Mac, IE Mac
else if (ygns6||(ygie && ygmac)){
ygar[1]="body,table{font-family:arial,helvetica,sans-serif;font-size:12px;line-height:1.5;}";
ygar[2]="tr,th,td{font-size:12px;line-height:16px;}";
ygar[3]="big{font-size:14px;line-height:18px;}";
ygar[4]="p{font-size:14px;line-height:18px;}";
ygar[5]="input,select{font-family:arial,helvetica,sans-serif;;font-size:12px;line-height:1.2;}";
ygar[6]="small{font-size:12px;}";}
// percentages: IE PC
else{
ygar[1]="body,th,td{font-family:arial,helvetica,sans-serif;font-size:12px;line-height:1.5;}";
ygar[2]="big{font-size:16px;line-height:1.2;}";
ygar[3]="p{font-size:16px;line-height:1.5;}";
ygar[4]="input,select{font-family:arial,helvetica,sans-serif;;font-size:12px;line-height:1.2;}";
ygar[5]="small{font-size:11px;}";}
var ygarjoin = ygar.join('');
ygd.write (ygarjoin);	
ygd.write(".ygcw{color:white;}.ygcb{color:black;}.ygfa{font-family:arial,sans-serif;}.ygfv{font-family:verdana,arial,sans-serif;}.ygft{font-family:times,serif;}.ygtb{font-size:18px;}.ygtbw{font-size:18px;color:white;}\n-->\n<\/style>");
// -->
