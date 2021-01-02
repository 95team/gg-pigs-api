package com.pangoapi._common.utility.github.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GitHubResponse {
    Content content;
    Commit commit;
}