package com.banking.account.exchange.rates;

import com.banking.account.exchange.rates.utils.TableDTOList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UberJSON {
    private List<TableDTOList> items;
}
