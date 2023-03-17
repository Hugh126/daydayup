package com.example.myspring.tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hugh
 * @version 1.0
 * @description:
 * @date 2021/2/13 0013
 */

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TEntry {

	private Integer id;
	private String name;
	private Integer sno;
	private String pass;
	private String email;

	public static TEntry create(String name, String pass, String email) {
		return TEntry.builder().name(name).pass(pass).email(email).build();
	}
}
