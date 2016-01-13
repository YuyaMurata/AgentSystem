cd C:/Users/kaeru/Documents/NetBeansProjects/AgentSystem/AgentSystem/logs/user10;
data=logs\user10\System-ALL_p[60s,1000ms,u100000,ag10,s1,st128,tMountType,L1000,ws100,w(100,10)].csv;
set datafile separator ",";
set xdata time;
set multiplot layout 3,3;
plot data using 1:6
plot data using 1:7
plot data using 1:8
plot data using 1:9
plot data using 1:10
plot data using 1:11
plot data using 1:12
plot data using 1:13
plot data using 1:14
unset multiplot;
