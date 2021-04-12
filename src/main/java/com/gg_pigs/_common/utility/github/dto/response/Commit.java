package com.gg_pigs._common.utility.github.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Commit {

    String sha;
    String node_id;
    String url;
}
