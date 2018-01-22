// SERVER_ID, SERVER_NAME, SERVER_HOST, SERVER_PORT, SERVER_DESCRIPTION
// List of servers to be displayed
var serverListTemplate = '\
<!-- server="SERVER_ID" -->\
<tr server="SERVER_ID" class="app-server-row">\
  <td><button onclick="launchServerAction(SERVER_ID)" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-play"></span></button></td>\
  <td>SERVER_NAME</td>\
  <td>SERVER_HOST</td>\
  <td>SERVER_PORT</td>\
  <td>SERVER_USERNAME</td>\
</tr>';

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
				<button onclick="toggleTunnelForm(SERVER_ID)" class="btn btn-default btn-xs" style="margin-top: -4px;"><span class="glyphicon glyphicon-plus-sign"></span> tunnel</button>\
				<button onclick="deleteServerAction(SERVER_ID)" class="btn btn-danger btn-xs pull-right" style="margin-top: 1px;"><span class="glyphicon glyphicon-trash"></span></button>\
			</div>\
		</div>\
	</div>';

var newTunnelListTemplate = '\
';

// SERVER_ID
// Commands row to be added after each server
var serverCommandsTemplate = '\
<tr id="cmdSrvSERVER_ID" class="app-expanded-server app-server-commands" style="display: none;">\
  <td colspan=4>\
    <div class="pull-left">\
	<button onclick="launchServerAction(SERVER_ID)" class="btn btn-primary"><span class="glyphicon glyphicon-play"></span></button>\
      <button onclick="toggleTunnelForm(SERVER_ID)" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Tunnel</button>\
    </div>\
    <div class="pull-right">\
      <span class="app-button-label">Server commands: </span>\
      <!--<button class="btn btn-default disabled"><span class="glyphicon glyphicon-edit"></span></button>-->\
      <button onclick="deleteServerAction(SERVER_ID)" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>\
    </div>\
  </td>\
</tr>';

// TABLE_ROWS
// Row containing Tunnel table
var tunnelListTemplate = '\
<tr id="tnlSrvSERVER_ID" class="app-expanded-server" style="display: none;">\
  <td colspan=4>\
    <div class="app-tunnel-table">\
      <table>\
        TABLE_ROWS \
      </table>\
    </div>\
  </td>\
</tr>';

// TUNNEL_ID, TUNNEL_NAME, LOCAL_PORT, REMOTE_HOST, REMOTE_PORT, TUNNEL_DESCRIPTION
// Tunnel row
var tunnelRowTemplate = '\
<tr>\
  <td tunnel=TUNNEL_ID>\
    <!--<button class="btn btn-primary btn-xs" onclick="alert()"><span class="glyphicon glyphicon-play"></span></button>-->\
  </td>\
  <td>TUNNEL_NAME</td>\
  <td>LOCAL_PORT</td>\
  <td>REMOTE_HOST</td>\
  <td>REMOTE_PORT</td>\
  <td>TUNNEL_DESCRIPTION</td>\
  <td>\
    <div class="pull-right">\
      <!--<button class="btn btn-default btn-xs"><span class="glyphicon glyphicon-edit"></span></button>-->\
      <button onclick="deleteTunnelAction(TUNNEL_ID)" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span></button>\
    </div>\
  </td>\
</tr>';


function replaceAll(str, find, replace) {
  return str.replace(new RegExp(find, 'g'), replace);
}

function addServer(json) {
	var obj = JSON.parse(json);

	var serverListDOM = document.getElementById("app-main-view");

	//code to add server row
	serverListDOM.insertAdjacentHTML('beforeend', createServerRow(obj));
	//code to add command row
//	serverListDOM.insertAdjacentHTML('beforeend', createCommandRow(obj));
	//code to add tunnel row
//	serverListDOM.insertAdjacentHTML('beforeend', createTunnelRow(obj));
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
	var table = "";
    for (var i = 0; i < server.tunnels.length; i++) {
        var updatedRow = tunnelRowTemplate;
        console.log(i);
        console.log(server.tunnels[i]);
        console.log(server.tunnels[i].displayName);
        updatedRow = replaceAll(updatedRow, 'TUNNEL_ID',server.tunnels[i].id);
        updatedRow = updatedRow.replace('TUNNEL_NAME', server.tunnels[i].displayName);
        updatedRow = updatedRow.replace('LOCAL_PORT', server.tunnels[i].localPort);
        updatedRow = updatedRow.replace('REMOTE_HOST', server.tunnels[i].remoteHost);
        updatedRow = updatedRow.replace('REMOTE_PORT', server.tunnels[i].remotePort);
        updatedRow = updatedRow.replace('TUNNEL_DESCRIPTION', server.tunnels[i].description);
        table = table.concat(updatedRow);
    }
    return table;
}

function createTunnelRow(server) {
	console.log("createTunnelRow");
	var table = createTunnelTable(server);
	var tunnelRow = tunnelListTemplate.replace('TABLE_ROWS', table);
	tunnelRow = replaceAll(tunnelRow, 'SERVER_ID', server.id);
	return tunnelRow;
}