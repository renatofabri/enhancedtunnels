var newServerListTemplate = '\
	<div class="row app-server-row" code="SERVER_ID">\
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
				<!--style="margin-top: -4px; on tunnel button-->\
                <button onclick="loadTunnelsModal(SERVER_ID)" class="btn btn-default btn-xs pull-right" style="margin-top: 1px;"><span class="glyphicon glyphicon-eye-open"></span> view tunnels</button>\
                <!--<button onclick="showConfirmationModal(&quot;server&quot;, SERVER_ID)" class="btn btn-danger btn-xs pull-right" style="margin-top: 1px;"><span class="glyphicon glyphicon-trash"></span></button>-->\
            </div>\
		</div>\
	</div>';

var newTunnelListTemplate = '<div id="serverSERVER_IDtunnels" class="serverXtunnels" style="display:none;">\
TABLE_ROWS\
</div>';

var newTunnelRowTemplate = '<div class="row app-tunnel-row" tunnel="TUNNEL_ID" code="TUNNEL_ID" launchable="IS_LAUNCHABLE">\
    <div class="col-md-1 CAN_PLAY">\
        PLAY_ICON\
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

var playIconStr = '<span class="glyphicon glyphicon-play"></span>';

var serverArray = [];
var tunnelArray = [];


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

function addPathToPutty(path) {
    var pDOM = document.getElementById("app-pathToPutty");
    pDOM.innerHTML = path;
}

function addLogLevel(log_level) {
    var pDOM = document.getElementById("app-logLevel");
    pDOM.innerHTML = log_level;
}

function addLogPath(log_path) {
    var pDOM = document.getElementById("app-logPath");
    pDOM.innerHTML = log_path;
}

function createServerRow(server) {

    serverArray.push(server);
	var serverRow = replaceAll(newServerListTemplate, "SERVER_ID", server.id);
	serverRow = serverRow.replace('SERVER_NAME', server.display_name);
	serverRow = serverRow.replace('SERVER_USERNAME', server.username);
	serverRow = serverRow.replace('SERVER_HOST', server.host);
	serverRow = serverRow.replace('SERVER_PORT', server.port);
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

        tunnelArray.push(server.tunnels[i]);

        // if tunnel is playable, add to quicklist
        if (server.tunnels[i].launchable == 'true') {
            quickAccessListDOM.insertAdjacentHTML('beforeend', addQuickAccessItem(server.display_name, server.tunnels[i]));
        }

        var updatedRow = newTunnelRowTemplate;
        console.log(i);
        console.log(server.tunnels[i]);
        console.log(server.tunnels[i].display_name);
        var tunnelable = (server.tunnels[i].username && server.tunnels[i].username != 'null');
        updatedRow = updatedRow.replace('IS_LAUNCHABLE', (tunnelable ? 'true' : 'false'))
        updatedRow = updatedRow.replace('CAN_PLAY', (tunnelable ? 'playable' : ''))
        updatedRow = updatedRow.replace('PLAY_ICON', (tunnelable ? playIconStr : ''))
        updatedRow = replaceAll(updatedRow, 'TUNNEL_ID',server.tunnels[i].id);
        updatedRow = updatedRow.replace('TUNNEL_NAME', server.tunnels[i].display_name);
        updatedRow = updatedRow.replace('LOCAL_PORT', server.tunnels[i].local_port);
        updatedRow = updatedRow.replace('REMOTE_HOST', server.tunnels[i].remote_host);
        updatedRow = updatedRow.replace('REMOTE_PORT', server.tunnels[i].remote_port);
        updatedRow = updatedRow.replace('TUNNEL_USERNAME', ( tunnelable ? server.tunnels[i].username : '-'));
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
    qckItem = qckItem.replace('TUNNEL_NAME', tunnel.display_name);
    qckItem = qckItem.replace('TUNNEL_USERNAME', tunnel.username);
    qckItem = qckItem.replace('LOCAL_PORT', tunnel.local_port);
    return qckItem;
}