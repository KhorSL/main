//@@author vsudhakar

package seedu.address.model.person;

import java.io.File;
import java.net.MalformedURLException;

import com.sun.media.jfxmedia.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Contact's avatar
 */
public class Avatar {
    public static final String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public static final String DEFAULT_AVATAR_DIRECTORY = "avatars/";
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private ObjectProperty<Image> avatarImage;
    private String avatarFilePath;
    private String avatarFileName;

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = MainApp.class.getResource(DEFAULT_AVATAR_IMAGE_PATH).getFile();
        Logger.logMsg(Logger.DEBUG, "USING DEFAULT PATH: " + this.avatarFilePath);
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        if (validFile(avatarFilePath)) {
            File imgFile = new File(avatarFilePath);
            this.avatarFilePath = avatarFilePath;
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    public static String getDirectoryPath(String imageFile) {
        return DEFAULT_AVATAR_DIRECTORY + imageFile;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    /**
     * Creates image object and object property
     * for UI to bind to
     *
     */
    public void constructImageProperty() {
        File imgFile = new File(this.avatarFilePath);
        Image imgObj = new Image(imgFile.toURI().toString());
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    /**
     *
     * @return Bindable object for UI
     */
    public ObjectProperty<Image> avatarImageProperty() {
        if (avatarImage == null) {
            constructImageProperty();
        }
        return avatarImage;
    }

    /**
     * validate the file path
     *
     * @param avatarFilePath file path
     * @return true or false
     */
    public static boolean validFile(String avatarFilePath) {
        try {
            File f = new File(avatarFilePath);
            Logger.logMsg(Logger.DEBUG, "File: " + avatarFilePath + " | Exists: " + Boolean.toString(f.exists()) + " | Can Read: " + Boolean.toString(f.canRead()));
            return f.exists() && f.canRead();
        } catch (NullPointerException e) {
            Logger.logMsg(Logger.ERROR, "Error reading file at: " + avatarFilePath);
            Logger.logMsg(Logger.ERROR, e.toString());
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Avatar
                && this.avatarFilePath.equals(((Avatar) other).avatarFilePath));
    }
}
