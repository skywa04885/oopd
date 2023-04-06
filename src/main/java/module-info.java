module nl.cas_en_luke.oopd_eindopdracht {
    requires javafx.controls;
    requires javafx.fxml;
    requires hanyaeger;
    requires commons.math3;
    requires java.logging;

    exports nl.cas_en_luke.oopd_eindopdracht;

    opens nl.cas_en_luke.oopd_eindopdracht.audio;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.scenes.start;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.scenes.game;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.scenes.victory;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.scenes.loss;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.maps.grass_field;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.maps.swamp;
    opens nl.cas_en_luke.oopd_eindopdracht.audio.maps.cornfield;
    opens nl.cas_en_luke.oopd_eindopdracht.fonts;
    opens nl.cas_en_luke.oopd_eindopdracht.images.scenes.loss;
    opens nl.cas_en_luke.oopd_eindopdracht.images.maps.swamp;
    opens nl.cas_en_luke.oopd_eindopdracht.images.maps.grass_field;
    opens nl.cas_en_luke.oopd_eindopdracht.images.maps.cornfield;
    opens nl.cas_en_luke.oopd_eindopdracht.images.balloons;
    opens nl.cas_en_luke.oopd_eindopdracht.images.scenes.game;
    opens nl.cas_en_luke.oopd_eindopdracht.images.scenes.start;
    opens nl.cas_en_luke.oopd_eindopdracht.images.scenes.victory;
    opens nl.cas_en_luke.oopd_eindopdracht.images.turrets;
    opens nl.cas_en_luke.oopd_eindopdracht.images.projectiles;
    exports nl.cas_en_luke.oopd_eindopdracht.spawners;
    opens nl.cas_en_luke.oopd_eindopdracht.spawners to javafx.fxml;
    exports nl.cas_en_luke.oopd_eindopdracht.timers;
    opens nl.cas_en_luke.oopd_eindopdracht.timers to javafx.fxml;
}