package com.bitlicon.purolator.util;

import com.bitlicon.purolator.BuildConfig;

import org.apache.log4j.Logger;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class LogUtil {

    /**
     * Don't use this when obfuscating class names!
     */

    private LogUtil() {
    }

    /**
     * Obtiene el nombre simplificado de una clase
     *
     * @param cls, Clase del cual se referencia
     * @return nombre de la clase en el formato: [nombre del paquete].[nombre de la clase]
     */
    public static String makeLogTag(Class cls) {
        return cls.getSimpleName();
    }

    /**
     * Registra los mensajes Logs de tipo VERBOSE
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGV(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(tag);
            log.trace(message);
        }
    }

    /**
     * Registra los mensajes Logs de tipo VERBOSE
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGV(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(tag);
            log.trace(message, cause);
        }
    }

    /**
     * Registra los mensajes Logs de tipo VERBOSE
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGV(Class cls, String message) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(cls);
            log.trace(message);
        }
    }

    /**
     * Registra los mensajes Logs de tipo VERBOSE
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGV(Class cls, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(cls);
            log.trace(message, cause);
        }
    }

    /**
     * Registra los mensajes Logs de tipo DEBUG
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGD(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(tag);
            log.debug(message);
        }
    }

    /**
     * Registra los mensajes Logs de tipo DEBUG
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGD(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(tag);
            log.debug(message, cause);
        }
    }

    /**
     * Registra los mensajes Logs de tipo DEBUG
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGD(Class cls, String message) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(cls);
            log.debug(message);
        }
    }

    /**
     * Registra los mensajes Logs de tipo DEBUG
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGD(Class cls, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Logger log = Logger.getLogger(cls);
            log.debug(message, cause);
        }
    }

    /**
     * Registra los mensajes Logs de tipo INFORMATION
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGI(final String tag, String message) {
        Logger log = Logger.getLogger(tag);
        log.info(message);
    }

    /**
     * Registra los mensajes Logs de tipo INFORMATION
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGI(final String tag, String message, Throwable cause) {
        Logger log = Logger.getLogger(tag);
        log.info(message, cause);
    }

    /**
     * Registra los mensajes Logs de tipo INFORMATION
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGI(Class cls, String message) {
        Logger log = Logger.getLogger(cls);
        log.info(message);
    }

    /**
     * Registra los mensajes Logs de tipo INFORMATION
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGI(Class cls, String message, Throwable cause) {
        Logger log = Logger.getLogger(cls);
        log.info(message, cause);
    }

    /**
     * Registra los mensajes Logs de tipo WARNING
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGW(final String tag, String message) {
        Logger log = Logger.getLogger(tag);
        log.warn(message);
    }

    /**
     * Registra los mensajes Logs de tipo WARNING
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGW(final String tag, String message, Throwable cause) {
        Logger log = Logger.getLogger(tag);
        log.warn(message, cause);
    }

    /**
     * Registra los mensajes Logs de tipo WARNING
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGW(Class cls, String message) {
        Logger log = Logger.getLogger(cls);
        log.warn(message);
    }

    /**
     * Registra los mensajes Logs de tipo WARNING
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGW(Class cls, String message, Throwable cause) {
        Logger log = Logger.getLogger(cls);
        log.warn(message, cause);
    }

    /**
     * Registra los mensajes Logs de tipo ERROR
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGE(final String tag, String message) {
        Logger log = Logger.getLogger(tag);
        log.error(message);
    }

    /**
     * Registra los mensajes Logs de tipo ERROR
     *
     * @param tag,     cabecera del mensaje que se registrara en el Log
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGE(final String tag, String message, Throwable cause) {
        Logger log = Logger.getLogger(tag);
        log.error(message, cause);
    }

    /**
     * Registra los mensajes Logs de tipo ERROR
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     */
    public static void LOGE(Class cls, String message) {
        Logger log = Logger.getLogger(cls);
        log.error(message);
    }

    /**
     * Registra los mensajes Logs de tipo ERROR
     *
     * @param cls,     objeto que contiene la clase desde donde es instanciada
     * @param message, contenido del mensaje que se registrará en el Log
     * @param cause,   objeto que contiene informacion de algun error encontrado
     */
    public static void LOGE(Class cls, String message, Throwable cause) {
        Logger log = Logger.getLogger(cls);
        log.error(message, cause);
    }

}
