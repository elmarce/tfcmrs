#!/usr/bin/perl -w

use strict;
use warnings;
use Data::Dumper;
use File::HomeDir;
use File::Basename;
use File::Find::Rule;
use List::Util qw(first max maxstr min minstr reduce shuffle sum);
use Statistics::Basic qw(:all);
use Statistics::Descriptive;
use Bio::SeqIO;
use Hash::Merge qw( merge );
use Cwd;
use Getopt::Long;

#===============================================================================
my (%opt);
my $res=GetOptions(\%opt,              
    'input|i=s',
    'outdir|o:s',
    'ED_Prozent|ED:f',
    'SOFTCLIPPING_Prozent|SC=f'
    ) ;
if (!defined $opt{input}) {
    print"basicSamFileParser.pl exited: no input file found\n";
    exit;
}

#===============================================================================
my $samfile                     = $opt{input};
my $ED_Prozent                  =  defined $opt{ED_Prozent} ? $opt{ED_Prozent}: 0.4;
my $SOFTCLIPPING_Prozent        =  defined $opt{SOFTCLIPPING_Prozent} ? $opt{SOFTCLIPPING_Prozent}: 0.2 ;
my $OUTPUTDIR                   = dirname($samfile);
my $FILENAME                    = basename($samfile);


&parseSam($samfile, $ED_Prozent, $SOFTCLIPPING_Prozent);
#===============================================================================
sub parseSam{
    my ($samfile, $ED_Prozent, $SCPR) = @_;
    my $outfile = $samfile;
    my $inputFileDir = dirname($samfile);
    unless(-d "$inputFileDir/results" ){
          mkdir("$inputFileDir/results", 0777);
     }
    my $OUTPUTDIRNAME = "$inputFileDir/results";
    
    my(%Hit_count, %GENES_READCOUNT, %GENES_SIGNAL, %SPECIES_READCOUNT, %SPECIES_GENES_READS);
    if ($samfile !~/.sam$/) {
        print "*FATAL* wrong file extension:\n";
        print "a .sam file is expected\n";
        print "==================================================\n";
        print "    $samfile\n";
        exit;
    }
    open(OUT, ">", "$OUTPUTDIRNAME/ReadCountPerGen.txt") or die "$!";
    print OUT"GENE\tREAD-COUNT\tSIGNAL\n";
    
    #my $SCPR = 0.2;
    my(%EDIT_DIST, %BWDFWD_HITS, %REF_BWDFWD_HITS, %QUALCOUNT_MAPPED, %SOFTCLIP_RATIO, %CIGAR, %SOFTCLIP_RATIO_ALL);
    open(SAM, "<", $samfile) or die "$!";
    while (<SAM>) {
        my $line  = $_;
        chomp($line);
        next if($line =~/^@/);
        my @fields = split(/\t/,$line);
        if( scalar @fields < 11 ) {
            print "*FATAL* The following files are invalid SAM files:\n";
            print "==================================================\n";
            print "    $line\n";
            exit;
        }else{
           next if($fields[1] & hex(0b100) || ($fields[2] eq "*"));#unmapped reads
           next if($fields[2] eq "*");#unmapped reads
           my @tmp = split(/[a-zA-Z]/, $fields[5]);
            #my @tmp = ($fields[5] =~ /(\d+)M/g);
            my @M = ($fields[5] =~ /(\d+)M/g);
            my @I = ($fields[5] =~ /(\d+)I/g);
            my @D = ($fields[5] =~ /(\d+)D/g);
            my @S = ($fields[5] =~ /(\d+)S/g);
            my $readLength = sum(@tmp);
            my $softclip_ratio = 0;
            my $softClippingCount = 0;
            if (@S) {
               $softClippingCount = sum(@S);
               $softclip_ratio = (sum(@S)/$readLength);
            }
               
           my $MQ = $fields[4];
           next if($MQ<2);
           my @edits = split(/\:/,$fields[12]);
           my $ED_Distance = $edits[2];
           next if(($ED_Distance/$readLength)> $ED_Prozent);
            
            if ($fields[5] =~ m/(\d+)M/) {
                if (defined $fields[2] && ($softclip_ratio <= $SCPR)) {
                     $GENES_READCOUNT{$fields[2]}++;
                     $GENES_SIGNAL{$fields[2]} += (($readLength -($ED_Distance + $softClippingCount))/($ED_Distance + $softClippingCount +1));
                }     
            }
        }
    }
    
    close(SAM);
    foreach my $gene(sort{$GENES_SIGNAL{$b} <=> $GENES_SIGNAL{$a}} keys %GENES_SIGNAL ){
        print OUT"$gene\t$GENES_READCOUNT{$gene}\t$GENES_SIGNAL{$gene}\n";
    }
    
    
    close(OUT);
}