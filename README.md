# User Guide

Duchess is an app for **managing tasks, optimised for use via a Command Line Interface (CLI)**. If you are comfortable with typing a lot, Duchess can help you with managing your todos, deadlines, and events in a quick and intuitive manner. This project was done as part of a requirement of CS2113.
## Quick Start

Prerequisites: JDK 11

1. Double click the `Duchess.java` file. A CLI should open, and your save file should be automatically loaded from "C://Documents/Duke/ip/data.txt". Otherwise, the directory and data.txt file will be automatically created.
1. Type the command in the CLI and press Enter to execute it. 
e.g. typing `list` and pressing Enter will list out all the tasks. Here are the commands you can try:
    * `list` : prints all current tasks
    * `done` `[task number]` : marks the task as done
    * `todo` `[description]`: adds a Todo task
    * `deadline` `[description]` `/by` `[time]`: adds a Deadline task
    * `event` `[description]` `/at` `[time]`: adds an Event task
    * `delete` `[task number]`: deletes a task
    * `find` `[query]`: searches all task descriptions for the search term
    * `bye`: terminates Duchess
  
 Note: the first word of each command has to be in lower case, and separated from the rest of the command by a space.
