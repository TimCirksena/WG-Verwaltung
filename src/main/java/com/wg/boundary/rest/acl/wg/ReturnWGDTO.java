package com.wg.boundary.rest.acl.wg;

import com.wg.entity.WG;

import javax.json.bind.annotation.JsonbProperty;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author majaesch & tcirksen
 *
 * */
public class ReturnWGDTO {
    public Long wgId;
    public String wgName;
    @JsonbProperty("_links")
    public Map<String, URI> links = new HashMap<>();

    public ReturnWGDTO(){}
    public ReturnWGDTO(WG wg){
        this.wgId = wg.getWgId();
        this.wgName = wg.getWgname();
    }

    public void addURI(String name, URI uri){
        this.links.put(name,uri);
    }
}
