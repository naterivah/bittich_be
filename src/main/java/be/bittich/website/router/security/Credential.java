package be.bittich.website.router.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Nordine on 10-11-15.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential implements Serializable {

    private String name;
    private String password;
}
