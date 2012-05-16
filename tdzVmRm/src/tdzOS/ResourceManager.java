/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tdzOS;

/**
 *
 * @author Tomas
 */
class ResourceManager {
    
    public OS os;
    
    ResourceManager(OS os)
    {
        this.os = os;
    }
    
    //paskirstytojas konkreciam procesui, duodant resursus, kurie skaidomi, pvz atminits.
    public void Execute(Process process, Resource r, int count)
    {
        //TODO
        
        //Prašydamas resurso ar norėdamas jį atlaisvinti,
        //procesas kreipiasi į atitinkamą resursų paskirstytoją, kuris privalo jį aptarnauti.
        //Resursų paskirstytojo paskirtis – suteikti paprašytą resurso elementų kiekį procesui.
        
        //turi būti numatyta galimybė atiduoti resurso elementą konkrečiam procesui,
        //arba prašyti kelių resurso elementų. Resurso paskirstytojas peržvelgia visus laukiančius
        //šio resurso procesų sąrašą, ir, sutikęs galimybę aptarnauti procesą,
        //perduoda jam reikalingus resurso elementus ir pažymi jį pasiruošusiu.
        
        os.processManager.Execute();
    }
    
    //paskirstytojas konkreciam procesui, duodant resursus kurie yra neskaidomi i komponentus
    public void Execute(Process process, Resource r)
    {
        //TODO
        
        os.processManager.Execute();
    }
    
    //paskirstytojas visiem laukiantiems resursams kai resursas skaidomas dalimis
    public void Execute(Resource r, int count)
    {
        //TODO
        
        os.processManager.Execute();
    }
    
    //paskirstytojas visiems laukiantiems resursams kai resursas neskaidomas
    public void Execute(Resource r)
    {
        //TODO
        
        os.processManager.Execute();
    }
}
