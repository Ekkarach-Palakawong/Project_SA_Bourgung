package ku.cs.models;

public class Prawn {
    private String id,species,detail;

    public Prawn(String id, String species, String detail) {
        this.id = id;
        this.species = species;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean checkPrawnId(String id){return this.id.equals(id);}
}
