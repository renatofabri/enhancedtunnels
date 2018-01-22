var newServerListTemplate = '\
	<div class="row">\
		<div id="serverSERVER_ID">\
			<div class="col-md-1 play">\
				<button onclick="launchServerAction(SERVER_ID)" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-play"></span></button>\
			</div>\
			<div id="serverNameSERVER_ID" class="col-md-4">\
				SERVER_NAME\
			</div>\
			<div class="col-md-2">\
				SERVER_USERNAME\
			</div>\
			<div class="col-md-2">\
				SERVER_HOST\
			</div>\
			<div class="col-md-1">\
				SERVER_PORT\
			</div>\
            <div class="col-md-2 commands">\
                <button onclick="loadTunnelsModal(SERVER_ID)" class="btn btn-default btn-xs" style="margin-top: -4px;"><span class="glyphicon glyphicon-eye-open"></span> view tunnels</button>\
                <button onclick="showConfirmationModal(&quot;server&quot;, SERVER_ID)" class="btn btn-danger btn-xs pull-right" style="margin-top: 1px;"><span class="glyphicon glyphicon-trash"></span></button>\
            </div>\
		</div>\
	</div>';

var newTunnelListTemplate = '<div id="serverSERVER_IDtunnels" class="serverXtunnels" style="display:none;">\
TABLE_ROWS\
</div>';

var newTunnelRowTemplate = '<div class="row" tunnel="TUNNEL_ID">\
    <div class="col-md-1">\
        Y\
    </div>\
    <div id="tunnelNameTUNNEL_ID" class="col-md-2">\
        TUNNEL_NAME\
    </div>\
    <div class="col-md-1">\
        LOCAL_PORT\
    </div>\
    <div class="col-md-2">\
        REMOTE_HOST\
    </div>\
    <div class="col-md-1">\
        REMOTE_PORT\
    </div>\
    <div class="col-md-2">\
        TUNNEL_USERNAME\
    </div>\
    <div class="col-md-2">\
        TUNNEL_DESCRIPTION\
    </div>\
    <div class="col-md-1">\
        <button onclick="showConfirmationModal(&quot;tunnel&quot;, TUNNEL_ID)" class="btn btn-danger btn-xs pull-right"><span class="glyphicon glyphicon-trash"></span></button>\
    </div>\
</div>\
';

var quickAccessTemplate = '<li class="playable" onclick="launchTunnelAction(TUNNEL_ID)">\
    <span><span class="glyphicon glyphicon-play"></span> TUNNEL_NAME</span>\
    <span>TUNNEL_USERNAME</span>\
    <span>LOCAL_PORT</span>\
    <span>On SERVER_NAME</span>\
</li>\
';


function replaceAll(str, find, replace) {
  return str.replace(new RegExp(find, 'g'), replace);
}

function addServer(json) {
	var obj = JSON.parse(json);

	//code to add server row
    var serverListDOM = document.getElementById("app-main-view");
	serverListDOM.insertAdjacentHTML('beforeend', createServerRow(obj));

	//code to add tunnel row
    var tunnelListDOM = document.getElementById("app-tunnel-list");
	tunnelListDOM.insertAdjacentHTML('beforeend', createTunnelRow(obj));

}

function createServerRow(server) {
	var serverRow = replaceAll(newServerListTemplate, "SERVER_ID", server.id);
	serverRow = serverRow.replace('SERVER_NAME', server.displayName);
	serverRow = serverRow.replace('SERVER_USERNAME', server.username);
	serverRow = serverRow.replace('SERVER_HOST', server.host);
	serverRow = serverRow.replace('SERVER_PORT', server.port);
	serverRow = serverRow.replace('SERVER_DESCRIPTION', server.description); 
	return serverRow;
}

function createCommandRow(server) {
	var commandRow = replaceAll(serverCommandsTemplate, 'SERVER_ID', server.id);
	return commandRow;
}

function createTunnelTable(server) {
	console.log("createTunnelTable");
    var quickAccessListDOM = document.getElementById("app-tunnel-quick-access-list");
    var table = "";
    for (var i = 0; i < server.tunnels.length; i++) {

//        if tunnel is playable, add to quicklist
        if (server.tunnels[i].launchable == 'true') {
            quickAccessListDOM.insertAdjacentHTML('beforeend', addQuickAccessItem(server.displayName, server.tunnels[i]));
        }

        var updatedRow = newTunnelRowTemplate;
        console.log(i);
        console.log(server.tunnels[i]);
        console.log(server.tunnels[i].displayName);
        updatedRow = replaceAll(updatedRow, 'TUNNEL_ID',server.tunnels[i].id);
        updatedRow = updatedRow.replace('TUNNEL_NAME', server.tunnels[i].displayName);
        updatedRow = updatedRow.replace('LOCAL_PORT', server.tunnels[i].localPort);
        updatedRow = updatedRow.replace('REMOTE_HOST', server.tunnels[i].remoteHost);
        updatedRow = updatedRow.replace('REMOTE_PORT', server.tunnels[i].remotePort);
        updatedRow = updatedRow.replace('TUNNEL_USERNAME', ((server.tunnels[i].username && server.tunnels[i].username != 'null') ? server.tunnels[i].username : '-'));
        updatedRow = updatedRow.replace('TUNNEL_DESCRIPTION', server.tunnels[i].description);
        table = table.concat(updatedRow);
    }
    return table;
}

function createTunnelRow(server) {
	console.log("createTunnelRow");
	var table = createTunnelTable(server);
	var tunnelRow = newTunnelListTemplate.replace('TABLE_ROWS', table);
	tunnelRow = replaceAll(tunnelRow, 'SERVER_ID', server.id);
	return tunnelRow;
}

function addQuickAccessItem(serverName, tunnel) {
    var qckItem = quickAccessTemplate.replace('SERVER_NAME', serverName);
    qckItem = qckItem.replace('TUNNEL_ID', tunnel.id);
    qckItem = qckItem.replace('TUNNEL_NAME', tunnel.displayName);
    qckItem = qckItem.replace('TUNNEL_USERNAME', tunnel.username);
    qckItem = qckItem.replace('LOCAL_PORT', tunnel.localPort);
    return qckItem;
}