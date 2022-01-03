# Gorilla (Game)


## Dependencies
Vi bruger <code>JavaFX</code>, Java version <code>17</code> med <code>Maven</code> som build-værktøj.


## Architecture
MVC (Model-View-Controller);
For at gøre det så let som muligt at have overblik over projektet især når det vokser, så prøv at overholde MVC, hvilket også gør det lettere at have flere der arbejder


## Projektstyring og -opgaver
Vi benytter os af det indbyggede <code>issues</code> og <code>Projects</code> i Github til at holde styr på opgaverne, der skal løses.



## Git contribution (Checkliste) 
Husk altid at hente (<code>pull</code>) den nyeste kode ned før vi påbegynder nyt arbejde.

Skriv kommentarar i koden.

Når vi laver <code>commits</code> så sørg for at navngive dem på en hensigtsmæssig måde, der kort beskriver ændringen. Eventuelt præfiks beskeden med ord som <code>fix:</code>, <code>feature:</code> mf. hvis det er muligt eller tag det issue som bliver løst.

Prøv kun at arbejde på en enkelt ting af gangen, og commit kun de filer der er relevante i forhold til ændringen. Hellere mange små commits end én stor!

Efter færdiggørelsen af/arbejdet med en opgave, så opdater den i projektmappen (kanban) eller direkte i dens <code>issue</code>. 

Opret gerne nye issues hvis I ser bugs, der ikke er beskrevet eller tænker på nye features. 

Arbejder I på en større feature kan I eventuelt commit til en branch i stedet for master.


## Automatiserede tests
Hver gang vi indsender kode, så har jeg sat et Github Action script (Action-panelet) op, som automatisk bygger java projektet i sin helhed og laver en <code>.jar</code> fil. Sørg for at det kode i indsender rent faktisk kan "bygges". 
