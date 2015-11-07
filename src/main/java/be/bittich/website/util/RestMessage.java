package be.bittich.website.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Nordine on 18-10-15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestMessage implements Serializable {

    private String message;
}
