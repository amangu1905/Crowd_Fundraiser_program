module CrowdFundingSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens crowdfund.controller to javafx.fxml;
    opens crowdfund.model to javafx.base;

    exports crowdfund;
}
