package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class loadFile {
    public List<obj> types = new ArrayList<>();
    private int noChineseObj = 0;

    public void init(){
        List<String> buff = new ArrayList<>();
        try {
            FileReader reader = new FileReader("C:\\Users\\dyong\\Desktop\\sde\\fsd\\typeIDs.yaml");
            BufferedReader br = new BufferedReader(reader);
            String str;
            while((str=br.readLine())!=null){
                //如果不是空格那就是开头
                if(str.length()>0 && !str.substring(0,1).equals(" ") && !str.substring(0,1).equals("\'")){
                    if(!buff.isEmpty()){
                        //创建一个object
                        getObj(buff);
                    }
                    buff.clear();
                    buff.add(str);
                }else{
                    buff.add(str);
                }
            }
        //打印obj数量
            System.out.println(types.size());
            System.out.println("有"+noChineseObj+"个物品不含中文名");
            int pub = 0;
            for(obj o:types){
                if(o.isPublished)
                    pub++;
            }
            System.out.println("有"+pub+"个公开物品");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getObj(List<String> e) {
        obj o = new obj();
        //先设置id
        String id = e.get(0);
        id = id.substring(0,id.indexOf(':'));
        o.id = Integer.parseInt(id);
        //设置名称
        String nameLine = null;
        boolean name =false;
        for(String s:e){
            if(!name){
                if(s.contains("name:")){
                    name = true;
                }
            }else{
                if(s.contains("zh")){
                    nameLine = s;
                    break;
                }
            }
        }
        if(nameLine != null && nameLine.contains("zh:")){
            o.name = nameLine.substring(nameLine.indexOf("zh:")+3,nameLine.length());
        }else{
            noChineseObj++;
        }
        //设置是否可见
        for(String s:e){
            if(s!=null && s.contains("published")){
                o.isPublished = Boolean.parseBoolean(s.substring(s.indexOf("published:")+10,s.length()).trim());
            }
        }
        types.add(o);
    }

}
