#!/bin/bash

# $1 Renew Path
# $2 Output Folder
counter=1
while [ $counter -le 30 ]
do 
    java -Dde.renew.netPath=./launch_script.sh -jar $1/renew2.5/loader.jar script simulation_run
    
    mv ./track_untreated_right.csv $2/track_untreated_right$counter.csv
    ((counter++))
    
done
    
echo "all done"
