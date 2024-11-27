package com.tallerwebi.infraestructura;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.tallerwebi.dominio.Plan;

public class PlanRowMapper implements RowMapper<Plan> {

        @Override
         public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
            Plan plan = new Plan();
            plan.setId(rs.getLong("id"));
            plan.setNombrePlan(rs.getString("nombrePlan")); 
            plan.setPrecio(rs.getDouble("precio")); 
            return plan;
         
    
}
}