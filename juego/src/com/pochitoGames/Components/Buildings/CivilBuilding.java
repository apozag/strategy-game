package com.pochitoGames.Components.Buildings;

import com.pochitoGames.Engine.Component;
import com.pochitoGames.Misc.ComponentTypes.TypeBuildingCivil;
import com.pochitoGames.Misc.ObjectTypes.WorkerObject;

public class CivilBuilding extends Component {

    private TypeBuildingCivil typeBuildingCivil;
    private final WorkerObject[] objectsNeeded = new WorkerObject[6];
    private final WorkerObject[] objectsProduced = new WorkerObject[6];
    private int level = 0;
    private int experience = 0;

    public int getExperience() {
        return experience;
    }

    public TypeBuildingCivil getTypeBuildingCivil() {
        return typeBuildingCivil;
    }

    public void setTypeBuildingCivil(TypeBuildingCivil typeBuildingCivil) {
        this.typeBuildingCivil = typeBuildingCivil;
    }

    public void checkExperience(){
        if (experience >= 20) increaseLevel();
    }


    public void setExperience(int experience) {
        if (experience <= 20) this.experience = experience;
        else this.experience = 0;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public WorkerObject[] getObjectsNeeded() {
        return objectsNeeded;
    }

    public WorkerObject[] getObjectsProduced() {
        return objectsProduced;
    }

    public void increaseLevel() {
        if (this.level > 10) {
            setLevel(++this.level);
        }
    }


    //Función tiene que ir con timming especial
    public boolean objectProduced () {
        for(int i = 0; i<objectsProduced.length;i++){
            if (objectsProduced[i] != null){
                objectsProduced[i] = WorkerObject.BREAD;
                return true;
            } else return false;
        } return false;
    }
}
