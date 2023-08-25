package com.example.wereL.utils;

import com.example.wereL.exception.NotValidRequestException;
import com.example.wereL.model.dto.*;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.model.entity.Role;
import com.example.wereL.model.entity.Part;
import com.example.wereL.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DtoUtils {
    private static final Logger logger = LoggerFactory.getLogger(DtoUtils.class);

    public RoleDTO[] roleToRoleDTOArray(final Set<Role> roles) {
        if (roles == null) {
            System.out.println("dto");
            throw new NotValidRequestException();
        }
        final RoleDTO[] roleDTOS = new RoleDTO[roles.size()];
        int index = 0;
        for (Role r : roles) {
            roleDTOS[index] = new RoleDTO(r.getName());
            index++;
        }
        return roleDTOS;
    }

    public Part partDtoToEntity(PartDTO partDto) {
        Part part = new Part();
        part.setPartNumber(partDto.getPartNumber());
        part.setProductionYear(partDto.getProductionYear());
        part.setPartName(partDto.getPartName());
        part.setAudioRecordName(partDto.getAudioRecordName());
        part.setCreatedAt(partDto.getCreatedAt());
        part.setComment(partDto.getComment());
        part.setFactoryNumber(partDto.getFactoryNumber());
        return part;
    }

    public User userDtoToEntity(UserDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
        user.setPassword(userDto.getPassword());
        Set<Role> roles = new HashSet<>();
        final RoleDTO[] roleDTOS = userDto.getRoles();
        int index = 0;
        for (RoleDTO r : roleDTOS) {
            roles.add(new Role(r.getName()));
            index++;
        }
        user.setRoles(roles);
        return user;
    }

    public List<String> roleSetToRoleList(final Set<Role> roles) {
        if (roles == null) {
            System.out.println("Lito");
            throw new NotValidRequestException();
        }
        int n = roles.size();
        final List<String> roleList = new ArrayList<>(n);
        for (Role x : roles)
            roleList.add(x.getName());
        return roleList;
    }

    public Map<String, Object> convertToCategoryReport(List<CategoryByTimeDTO> categoryByTimeDTOs) {

        Map<String, Object> map = new HashMap<>();
        Object[] years = categoryByTimeDTOs.stream()
                .map(c -> (c.getDate().substring(0, 4)))
                .distinct()
                .toArray();
        logger.info(years.toString());
        Object[][] arr = new Object[12][years.length + 1];
        Object[] chartColumns = new Object[years.length + 1];
        chartColumns[0] = "Month";
        System.arraycopy(years, 0, chartColumns, 1, years.length);
        arr[0][0] = "Jan";
        arr[1][0] = "Feb";
        arr[2][0] = "Mar";
        arr[3][0] = "Apr";
        arr[4][0] = "May";
        arr[5][0] = "Jun";
        arr[6][0] = "Jul";
        arr[7][0] = "Aug";
        arr[8][0] = "Sep";
        arr[9][0] = "Oct";
        arr[10][0] = "Nov";
        arr[11][0] = "Dec";

        if (categoryByTimeDTOs.isEmpty()) {

            return idleMap();
        }

        int year1 = Integer.parseInt((String) years[0]);
        int j = 1;

        //     for (int j = 1; j <= years.length; j++) {
        for (CategoryByTimeDTO element : categoryByTimeDTOs) {
            // logger.info(String.valueOf((element.getDate().substring(6, 7)==("0" + (i + 1)))));
            int index = element.getDate().lastIndexOf('/');
            int year = Integer.parseInt(element.getDate().substring(0, 4));
            int length = element.getDate().length();
           // logger.info("element: {}", element.getDate().substring(index + 1, length));

            for (int i = 0; i < 12; i++) {

                //logger.info("month: {}", i);

                if (Integer.parseInt(element.getDate().substring(index + 1, length)) == ((i + 1))) {

                    if (year > year1) {
                        j++;
                        logger.info("j: {}", j);
                        logger.info("year1 before: {}", year1);
                        year1 = year;
                        logger.info("year1 after: {}", year1);
                    }
                   // logger.info("before insert: " + i + " " + j);
                    arr[i][j] = element.getValue();

                    //logger.info("element: {}", element.getDate() + " " + element.getValue());
                   // logger.info("j: {}", j);

                }
            }
        }
        map.put("chartColumns", chartColumns);
        map.put("arr", arr);
        //System.out.println(map.toString());
        return map;
    }

    public Map<String, Object> idleMap() {
        Map<String, Object> map1 = new HashMap<>();
        Object[][] arr = new Object[12][2];
        Object[] chartColumns1 = new Object[2];
        chartColumns1[0] = "Month";
        chartColumns1[1] = "No data";
        arr[0][0] = "Jan";
        arr[1][0] = "Feb";
        arr[2][0] = "Mar";
        arr[3][0] = "Apr";
        arr[4][0] = "May";
        arr[5][0] = "Jun";
        arr[6][0] = "Jul";
        arr[7][0] = "Aug";
        arr[8][0] = "Sep";
        arr[9][0] = "Oct";
        arr[10][0] = "Nov";
        arr[11][0] = "Dec";
        for (int i = 0; i < 12; i++) {
            arr[i][1] = 0;
        }
        map1.put("arr", arr);

        map1.put("chartColumns", chartColumns1);
        return map1;
    }

    public ExpenseTitleDTO expenseTitleToExpenseTitleDTO(final ExpenseTitle expenseTitle) {
        final ExpenseTitleDTO expenseTitleDTO = new ExpenseTitleDTO(Long.parseLong(String.valueOf(expenseTitle.getId())),
                expenseTitle.getExpenseName(),String.valueOf(expenseTitle.getCategory().getId()),
                expenseTitle.getCategory().getCategoryName());
        return expenseTitleDTO;
    }

}
