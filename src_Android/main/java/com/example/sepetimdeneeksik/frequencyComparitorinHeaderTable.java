package com.example.sepetimdeneeksik;

import java.util.Comparator;

class frequencyComparitorinHeaderTable implements Comparator<FPtree>{

    public frequencyComparitorinHeaderTable() {
    }

    public int compare(FPtree o1, FPtree o2) {
        if(o1.count>o2.count){
            return 1;
        }
        else if(o1.count < o2.count)
            return -1;
        else
            return 0;
    }

}


