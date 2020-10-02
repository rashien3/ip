# User Guide

## Table of Contents
[1. Introduction](#intro)<br>
[2. Setting Up](#setup)<br>
[3. Features](#features)<br>
[4. Usage](#usage)<br>

## <a name="intro">1. Introduction</a>
Duchess is an app for **managing tasks, optimised for use via a Command Line Interface (CLI)**. If you are comfortable with typing a lot, Duchess can help you with managing your todos, deadlines, and events in a quick and intuitive manner. This project was done as part of a requirement of CS2113.

## <a name="setup">2. Setting Up</a>
Prerequisites: JDK 11

1. Open the command line.
1. Locate where your ip.jar file is. Navigate to the file location using the command `cd [file location]`.
1. Enter the command `java -jar ip.jar`. The program should run and your save file should be automatically loaded from `~/Documents/Duke/ip/data.txt`. Otherwise, the directory and data.txt file will be automatically created.
1. Type the command in the CLI and press Enter to execute it. 
## <a name="features">3. Features</a> 

### Add Task
There are three classes of tasks:

`deadline`
`event`
`todo`

All tasks must be followed by a description, and `deadline` and `event` tasks must have a time specified by the user.

### List Tasks
Prints a list of all existing tasks. The following information will be provided
1. Index number
1. Task class:
`[T]` - todo
`[E]` - event
`[D]` - deadline
1. Whether the task is done or not:  
`[Y]` - Task is done  
`[N]` - Task is not done yet
1. Description of task
1. Time of task (for `deadline` and `event` tasks only)

After adding the new task, the program automatically saves.

### Delete Task
Delete a task, then saves.

### Mark Task as Done
Mark a task as done, then saves.

### Find Task
Search for all tasks containing a search query.

### Exit Program
Terminates the program.

## <a name="usage">4. Usage</a>

### `todo` - Add todo

Add a todo task.

Format:

`todo` `[DESCRIPTION]`

Example of usage: 

`todo laundry`

Expected outcome:

```
____________________________________________________________
Got it. I've added this task:
	[T][N] laundry
Now you have 4 tasks in the list.
____________________________________________________________
```

### `event` - Add event

Add an event task.

Format:

`event [DESCRIPTION] /at [TIME]`

Example of usage: 

`event meeting /at tomorrow`

Expected outcome:

```
____________________________________________________________
Got it. I've added this task:
	[E][N] meeting (at: tomorrow)
Now you have 5 tasks in the list.
____________________________________________________________
```

### `deadline` - Add deadline

Add a deadline task.

Format:

`deadline [DESCRIPTION] /by [TIME]`

Example of usage: 

`deadline quiz 2 /by Saturday 2359`

Expected outcome:

```
____________________________________________________________
Got it. I've added this task:
	[D][N] quiz 2 (by: Saturday 2359)
Now you have 6 tasks in the list.
____________________________________________________________
```

### `list` - List all tasks

Prints a list of all tasks
  
Example of usage: 

`list`

Expected outcome:

```
Here are the tasks in your list:
 1. [T][N]  read Java coding standards
 2. [E][N]  CS2113 Tutorial  (at: [Thursday 2-4pm])
 3. [D][N]  complete ip  (by: [Friday])
```

### `delete` - Delete Task

Removes the specified task.

Format:

`delete [TASK NO.]`

Example of usage: 

`delete 2`

Expected outcome:

```
____________________________________________________________
delete 2
Noted. I've removed this task:
	[E][N] CS tutorial (at: 2pm)
Now you have 6 tasks in the list.
____________________________________________________________
```

### `done` - Mark task as completed

Marks the specified task as done.

Format:

`done [TASK NO.]`

Example of usage: 

`done 2`

Expected outcome:

```
____________________________________________________________
done 3
Nice! I've marked this task as done:
	3. [T][Y] laundry
____________________________________________________________
```

### `find` - Find tasks

Narrow the search for particular tasks. A keyword has to be entered.

Format:

`find [QUERY]`

Example of usage: 

`find laund`

Expected outcome:

```
____________________________________________________________
Here are the matching tasks in your list:
1. [T][N] laundry
____________________________________________________________
```

### `bye` - Exit the program

Terminates the program.

Example of usage: 

`bye`

Expected outcome:

```
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
