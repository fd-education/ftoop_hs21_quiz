# FTOOP Project "Quiz"
This quiz is played as follows:
- One or more players connect to a game server.
- Every player picks a name which is denied if the name is already taken.
- The players play multiple rounds, one for every question the server has for this game.
- In every round, players have sixty seconds to choose one out of three answer options (A to C).
- Then, a feedback and a scoreboard are displayed.
- The game ends when the last round has been played. All players are then automatically disconnected.

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

## Custom question catalogs
Example files with questions are provided in the fragenkataloge directory, but custom question files can be used as well. The game expects the questions in the following format:
- The question ends with a question mark.
- The question is followed by three answers A, B and C.
- One answer is exactly one line, where the letter of the question (A, B or C) is at the start of the line and separated from the actual answer with a whitespace.
- The letter (A, B or C) of exactly one answer is followed by an asterisk (*). This marks the correct answer.

## Configuration
Per default, the server uses the following configuration:
- 2 players
- 5 questions
- Port 3141

To configure these values for your needs, use the java -D commandline option.
For example, to play with 4 players, use:

`java -jar -DplayerCount=4 server/target/server-1.0.jar fragenkataloge/fragenkatalog_2019.txt`

Use playerCount to configure the amount of players, questionCount for the amount of questions and thus rounds and port to change the port the server uses. 
## Maintainers
Planned and written with <3 by

Sebastian Schneuwly

Fabian Diemand

Nicola Bühler

during the advanced programming course taught by Mr. Senften at Fernfachhochschule Schweiz.