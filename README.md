# tugas-praktik-sem5

public static String kursIndonesia(double angka){
        DecimalFormat formatRp = new DecimalFormat("#,###");
        return "Rp. " + formatRp.format(angka);
    }