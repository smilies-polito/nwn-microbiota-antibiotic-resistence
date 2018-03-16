#!/bin/bash
counter=1
while [ $counter -le 30 ]
do 
    java -Dde.renew.netPath=./launch_script.sh -jar $1/renew2.5/loader.jar script simulation_run
    
    mv track_high_dosage_antibiotics_and_BbY.csv $2/track_high_dosage_antibiotics_and_BbY$counter.csv
    ((counter++))
    
done
    
echo "all done"
