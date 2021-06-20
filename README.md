# FTOOP Projekt "Quiz"
## Setup
For a proper user interface, use PowerShell to run the clients and run this command: 

`Set-ItemProperty HKCU:\Console VirtualTerminalLevel -Type DWORD 1`

## Usage
Compile the project: `mvn clean package`

To play the game, a game server that clients can connect to needs to be started.
The server expects the filename of a question catalog.
Example files are provided in the fragenkataloge directory.

Example: Start the server with [fragenkatalog_2019.txt](fragenkataloge/fragenkatalog_2019.txt)

`java -jar server/target/server-1.0.jar fragenkataloge/fragenkatalog_2019.txt`

Then start at least one client:

`java -jar client/target/client-1.0.jar`

## Configuration
Per default, the server uses the following configuration:
- 2 players
- 5 questions
- Port 3141

To configure these values for your needs, use the java -D commandline option.
For example, to play with 4 players, use:

`java -jar -DplayerCount=4 server/target/server-1.0.jar fragenkataloge/fragenkatalog_2019.txt`

Use playerCount to configure the amount of players, questionCount for the amount of questions and thus rounds and port to change the port the server uses. 

## Configuration of the Client
Per default, the client uses the following configuration:
- Host "localhost"
- Port 3141

To configure these values for your needs, use the java -D commandline option.
Use the following pattern:

`java -jar -Dhost="<host>" -Dport=<port> client/target/client-1.0.jar`

## Maintainers
Written with <3 by

Sebastian Schneuwly

Fabian Diemand

Nicola Bühler