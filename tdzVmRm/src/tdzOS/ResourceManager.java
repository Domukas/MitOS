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
        //jei yra procesu kuriems res reikalingas
        if(r.rd.waitingProcesses.size() != 0)
        {
            //tada ziurim ar toks resursas galimas atiduoti
           // r.rd.externalIDas
        }
        
        os.processManager.Execute();
    }
    
    //paskirstytojas visiems laukiantiems resursams kai resursas neskaidomas
    public void Execute(Resource r)
    {
        //jei yra procesu kuriems res reikalingas
        if (r.rd.waitingProcesses.size() != 0)
        {
            int priority = 0;
            Process tmpProcess = null;
            //tai randam su didziausiu prioritetu procesa
            for(int i = 0; i < r.rd.waitingProcesses.size(); i++)
            {
                if(priority < r.rd.waitingProcesses.get(i).pd.priority)
                {
                    tmpProcess = r.rd.waitingProcesses.get(i);
                }
            }
            //jam duodam resurso nuoroda ir pasalinam resursa is laisvu res saraso.
            tmpProcess.pd.ownedResources.add(r);
            os.resources.remove(r);
            //jei tam procesui daugiau jokiu resursu nereikia, pazymim ji pasiruosusiu
            boolean stillNeeded = false;
            for(int i = 0; i < os.resources.size(); i++)
            {
                for(int j = 0; j < os.resources.get(i).rd.waitingProcesses.size(); j++)
                {
                    if(tmpProcess == os.resources.get(i).rd.waitingProcesses.get(j))
                    {
                        stillNeeded = true;
                    }
                }
            }
            if(!stillNeeded)
            {
                tmpProcess.pd.state =  OS.ProcessState.Ready;
            }
        } 
        //kvieciam procesu planuotoja
        os.processManager.Execute();
    }
}
