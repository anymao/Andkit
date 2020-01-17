package com.anymore.wanandroid.repository.cookies

import okhttp3.Cookie
import timber.log.Timber
import java.io.*
import java.util.*

class SerializableCookie : Serializable {


    @Transient
    private var cookie: Cookie? = null

    fun encode(cookie: Cookie): String? {
        this.cookie = cookie

        val byteArrayOutputStream = ByteArrayOutputStream()
        var objectOutputStream: ObjectOutputStream? = null

        try {
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(this)
        } catch (e: IOException) {
            Timber.e(e, "IOException in encodeCookie")
            return null
        } finally {
            try {
                // Closing a ByteArrayOutputStream has no effect, it can be used later (and is used in the return statement)
                objectOutputStream?.close()
            } catch (e: IOException) {
                Timber.e(e, "Stream not closed in encodeCookie")
            }

        }

        return byteArrayToHexString(byteArrayOutputStream.toByteArray())
    }

    fun decode(encodedCookie: String): Cookie? {

        val bytes = hexStringToByteArray(encodedCookie)
        val byteArrayInputStream = ByteArrayInputStream(
            bytes
        )

        var cookie: Cookie? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as SerializableCookie).cookie
        } catch (e: IOException) {
            Timber.e(e, "IOException in decodeCookie")
        } catch (e: ClassNotFoundException) {
            Timber.e(e, "ClassNotFoundException in decodeCookie")
        } finally {
            try {
                objectInputStream?.close()
            } catch (e: IOException) {
                Timber.e(e, "Stream not closed in decodeCookie")
            }

        }
        return cookie
    }

    @Throws(IOException::class)
    private fun writeObject(oos: ObjectOutputStream) {
        oos.writeObject(cookie!!.name)
        oos.writeObject(cookie!!.value)
        oos.writeLong(if (cookie!!.persistent) cookie!!.expiresAt else NON_VALID_EXPIRES_AT)
        oos.writeObject(cookie!!.domain)
        oos.writeObject(cookie!!.path)
        oos.writeBoolean(cookie!!.secure)
        oos.writeBoolean(cookie!!.httpOnly)
        oos.writeBoolean(cookie!!.hostOnly)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(ois: ObjectInputStream) {
        val builder = Cookie.Builder()

        builder.name(ois.readObject() as String)

        builder.value(ois.readObject() as String)

        val expiresAt = ois.readLong()
        if (expiresAt != NON_VALID_EXPIRES_AT) {
            builder.expiresAt(expiresAt)
        }

        val domain = ois.readObject() as String
        builder.domain(domain)

        builder.path(ois.readObject() as String)

        if (ois.readBoolean())
            builder.secure()

        if (ois.readBoolean())
            builder.httpOnly()

        if (ois.readBoolean())
            builder.hostOnlyDomain(domain)

        cookie = builder.build()
    }

    companion object {

        private const val serialVersionUID = -8594045714036645534L

        /**
         * Using some super basic byte array &lt;-&gt; hex conversions so we don't
         * have to rely on any large Base64 libraries. Can be overridden if you
         * like!
         *
         * @param bytes byte array to be converted
         * @return string containing hex values
         */
        private fun byteArrayToHexString(bytes: ByteArray): String {
            val sb = StringBuilder(bytes.size * 2)
            for (element in bytes) {
                val v = element.toInt() and 0xff
                if (v < 16) {
                    sb.append('0')
                }
                sb.append(Integer.toHexString(v))
            }
            return sb.toString().toUpperCase(Locale.US)
        }

        /**
         * Converts hex values from strings to byte array
         *
         * @param hexString string of hex-encoded values
         * @return decoded byte array
         */
        private fun hexStringToByteArray(hexString: String): ByteArray {
            val len = hexString.length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character
                    .digit(hexString[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }

        private const val NON_VALID_EXPIRES_AT = -1L
    }

}